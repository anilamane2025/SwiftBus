package com.anil.swiftBus.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    
    
    
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder, CityService cityService) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.cityService = cityService;
	}

	@GetMapping("/sign-up")
    public String showSignUpForm(Model model) {
		model.addAttribute("registrationDTO", new RegistrationDTO());
        model.addAttribute("states", cityService.getAllStates());
        model.addAttribute("cities", cityService.getAllCities());
        
        return "signup";
    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registrationDTO") RegistrationDTO registrationDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	System.out.println("sadsd"+result);
        	List<City> cities = cityService.getAllCities();
            List<String> states = cityService.getAllStates();

            model.addAttribute("cities", cities);
            model.addAttribute("states", states);
            
            return "signup";
        }

        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords do not match");
            model.addAttribute("states", cityService.getAllStates());
            model.addAttribute("cities", cityService.getAllCities());
            return "signup";
        }
        
        if (userService.usernameExists(registrationDTO.getUsername())) {
            result.rejectValue("username", "error.registrationDTO", "Username already exists");
        }
        
        if (userService.phoneNumberExists(registrationDTO.getPhoneNumber())) {
                result.rejectValue("phoneNumber", "error.registrationDTO", "Phone number already exists");
        }

        if (result.hasErrors()) {
            List<City> cities = cityService.getAllCities();
            List<String> states = cityService.getAllStates();

            model.addAttribute("cities", cities);
            model.addAttribute("states", states);

            return "signup";
        }

        userService.save(registrationDTO);

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
    public String updateProfile(@PathVariable("id") Long id, @Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
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

            return "view-profile";
        }

        // update
        userService.update(id, userDTO);
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

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/user/all";  // Redirect to list of users after deletion
    }

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user-list";  // View for displaying a list of users
    }
}