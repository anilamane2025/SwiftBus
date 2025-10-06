package com.anil.swiftBus.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anil.swiftBus.dto.ChangePasswordDTO;
import com.anil.swiftBus.dto.RegistrationDTO;
import com.anil.swiftBus.dto.UserDTO;
import com.anil.swiftBus.entity.City;
import com.anil.swiftBus.service.CityService;
import com.anil.swiftBus.service.UserService;

@Controller
public class UserController {

    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder;
    
    private CityService cityService;
    
    private UserDetailsService userDetailsService;
    
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder, CityService cityService,UserDetailsService userDetailsService) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.cityService = cityService;
		this.userDetailsService = userDetailsService;
	}

	@GetMapping("/sign-up")
    public String showSignUpForm(@RequestParam(value = "redirect", required = false) String redirect,Model model) {
		System.out.println("called /sign-up end point in the method of showSignUpForm..");
		model.addAttribute("registrationDTO", new RegistrationDTO());
        model.addAttribute("states", cityService.getAllStates());
        model.addAttribute("cities", cityService.getAllCities());
        if (redirect != null) {
            model.addAttribute("redirectUrl", redirect);
        }
        return "signup";
    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registrationDTO") RegistrationDTO registrationDTO, BindingResult result, Model model,HttpSession session,@RequestParam(value = "redirect", required = false) String redirect,Authentication authentication) {
    	System.out.println("🔹 /register endpoint कॉल हुआ - registerUser method शुरू...");
        if (result.hasErrors()) {
        	System.out.println("❌ Form validation में error है, signup पेज पर वापस भेजा जा रहा है...");
        	System.out.println("sadsd"+result);
        	List<City> cities = cityService.getAllCities();
            List<String> states = cityService.getAllStates();

            model.addAttribute("cities", cities);
            model.addAttribute("states", states);
            
            return "signup";
        }

        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
        	System.out.println("❌ Password और Confirm Password मैच नहीं कर रहे हैं...");
            model.addAttribute("passwordError", "Passwords do not match");
            model.addAttribute("states", cityService.getAllStates());
            model.addAttribute("cities", cityService.getAllCities());
            return "signup";
        }
        
        if (userService.usernameExists(registrationDTO.getUsername())) {
        	System.out.println("❌ यह Username पहले से मौजूद है: " + registrationDTO.getUsername());
            result.rejectValue("username", "error.registrationDTO", "Username already exists");
        }
        
        if (userService.phoneNumberExists(registrationDTO.getPhoneNumber())) {
        	System.out.println("❌ यह Phone Number पहले से मौजूद है: " + registrationDTO.getPhoneNumber());
            result.rejectValue("phoneNumber", "error.registrationDTO", "Phone number already exists");
        }

        if (result.hasErrors()) {
        	System.out.println("❌ Error(s) मिलने की वजह से signup पेज पर वापस भेजा जा रहा है...");
            List<City> cities = cityService.getAllCities();
            List<String> states = cityService.getAllStates();

            model.addAttribute("cities", cities);
            model.addAttribute("states", states);

            return "signup";
        }
        
        Boolean verified = (Boolean) session.getAttribute("OTP_VERIFIED");
        String verifiedPhone = (String) session.getAttribute("OTP_PHONE");

        if (verified == null || !verified || !registrationDTO.getPhoneNumber().equals(verifiedPhone)) {
            System.out.println("❌ OTP verification missing/phone mismatch. Signup रोक दिया गया।");
            model.addAttribute("otpError", "कृपया पहले मोबाइल OTP वेरिफ़ाई करें।");
            model.addAttribute("states", cityService.getAllStates());
            model.addAttribute("cities", cityService.getAllCities());
            return "signup";
        }
        
        System.out.println("✅ सभी validations पास हो गए, नया user save किया जा रहा है...");
        userService.save(registrationDTO);
        
        session.removeAttribute("OTP_VERIFIED");
        session.removeAttribute("OTP_PHONE");
        
     // Auto login newly registered user
        UserDetails updatedUserDetails = userDetailsService.loadUserByUsername(registrationDTO.getUsername());

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                registrationDTO.getPassword(),
                updatedUserDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(newAuth);
        
        
        if (redirect != null && !redirect.isEmpty()) {
            return "redirect:" + redirect;
        }
        
        System.out.println("➡️ User को login पेज पर redirect किया जा रहा है, registration successful flag के साथ...");
        return "redirect:/login?registered=true";
    }
    
    @GetMapping("/view-profile")
    public String adminViewProfile() {
        return "view-profile"; 
    }
    
    @GetMapping("/profile/{id}")
    public String viewProfile(@PathVariable("id") Long id, Model model) {
        Optional<UserDTO> userDTO = userService.findById(id);
        if (userDTO.isPresent()) {
        	
            model.addAttribute("user", userDTO.get());
            model.addAttribute("userdetail", userDTO.get());
            List<City> cities = cityService.getAllCities(); // load from DB
            List<String> states = cityService.getAllStates();

            model.addAttribute("cities", cities);
            model.addAttribute("states", states);
            
            ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
            changePasswordDTO.setId(id);
            model.addAttribute("changePasswordDTO", changePasswordDTO);

            return "view-profile";  // View name
        }
        model.addAttribute("error", "User not found");
        return "error";
    }

    @PostMapping("/profile/{id}")
    public String updateProfile(@PathVariable("id") Long id, @Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model,Authentication authentication) {
    	Optional<UserDTO> existingUserOpt = userService.findById(id);

        if (existingUserOpt.isPresent()) {
            model.addAttribute("userdetail", existingUserOpt.get());
        }

        if (result.hasErrors()) {
            System.out.println("error : " + result);
            List<City> cities = cityService.getAllCities();
            List<String> states = cityService.getAllStates();

            model.addAttribute("cities", cities);
            model.addAttribute("states", states);
            
            ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
            changePasswordDTO.setId(id);
            model.addAttribute("changePasswordDTO", changePasswordDTO);

            return "view-profile";
        }

        UserDTO existingUser = existingUserOpt.get();

        // Check if username changed
        if (!existingUser.getUsername().equals(userDTO.getUsername())) {
            if (userService.usernameExists(userDTO.getUsername())) {
                result.rejectValue("username", "error.user", "Username already exists");
            }
        }

        // Check if phone number changed
        if (!existingUser.getPhoneNumber().equals(userDTO.getPhoneNumber())) {
            if (userService.phoneNumberExists(userDTO.getPhoneNumber())) {
                result.rejectValue("phoneNumber", "error.user", "Phone number already exists");
            }
        }

        if (result.hasErrors()) {
            List<City> cities = cityService.getAllCities();
            List<String> states = cityService.getAllStates();

            model.addAttribute("cities", cities);
            model.addAttribute("states", states);
            
            ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
            changePasswordDTO.setId(id);
            model.addAttribute("changePasswordDTO", changePasswordDTO);

            return "view-profile";
        }

        // update
        userService.update(id, userDTO);
        String username = authentication.getName();
        UserDetails updatedUserDetails = userDetailsService.loadUserByUsername(username);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                authentication.getCredentials(),
                updatedUserDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return "redirect:/profile/" + id;
    }
    
    @PostMapping("/profile/change-password/{id}")
    public String changePassword(@PathVariable Long id,
                                 @Valid @ModelAttribute("changePasswordDTO") ChangePasswordDTO dto,
                                 BindingResult result,
                                 Model model) {

    	Optional<UserDTO> userDTO = userService.findById(id);
        if (userDTO.isPresent()) {
        	
            model.addAttribute("user", userDTO.get());
            model.addAttribute("userdetail", userDTO.get());
            List<City> cities = cityService.getAllCities(); // load from DB
            List<String> states = cityService.getAllStates();

            model.addAttribute("cities", cities);
            model.addAttribute("states", states);
        }
        if (result.hasErrors()) {
            model.addAttribute("passwordError", "Please fill both fields correctly");
            return "view-profile"; // reload profile with errors
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords do not match!");
            return "view-profile";
        }

        userService.updatePassword(id, dto.getNewPassword());

        return "redirect:/profile/" + id + "?passwordChanged=true";
    }


}