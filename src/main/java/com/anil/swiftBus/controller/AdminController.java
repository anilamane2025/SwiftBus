package com.anil.swiftBus.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.anil.swiftBus.dto.AgentDTO;
import com.anil.swiftBus.dto.AgentRegisterDTO;
import com.anil.swiftBus.dto.ChangePasswordDTO;
import com.anil.swiftBus.dto.RegistrationDTO;
import com.anil.swiftBus.dto.UserDTO;
import com.anil.swiftBus.entity.City;
import com.anil.swiftBus.service.CityService;
import com.anil.swiftBus.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
    private UserService userService;
	
	@Autowired
	private CityService cityService;
    
    @GetMapping({"/home","/home.html","","/"})
    public String adminHome() {
        return "home"; 
    }
    
    @GetMapping({"/passenger-list"})
    @PreAuthorize("hasAuthority('PASSENGER_VIEW')")
    public String listPassengers(Model model) {
        model.addAttribute("passengers", userService.findPassengers());
         
        return "passenger-list";
    }
    
    @GetMapping("/passenger/register")
    public String showSignUpForm(Model model) {
		model.addAttribute("registrationDTO", new RegistrationDTO());
        model.addAttribute("states", cityService.getAllStates());
        model.addAttribute("cities", cityService.getAllCities());
        
        return "passenger-add";
    }
    
    @PostMapping("/passenger/register")
    public String registerUser(@Valid @ModelAttribute("registrationDTO") RegistrationDTO registrationDTO, BindingResult result, Model model,RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
        	System.out.println("sadsd"+result);
        	List<City> cities = cityService.getAllCities();
            List<String> states = cityService.getAllStates();

            model.addAttribute("cities", cities);
            model.addAttribute("states", states);
            return "passenger-add";
        }

        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords do not match");
            model.addAttribute("states", cityService.getAllStates());
            model.addAttribute("cities", cityService.getAllCities());
            
            return "passenger-add";
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
            return "passenger-add";
        }

        try {
        	userService.save(registrationDTO);
            redirectAttributes.addFlashAttribute("success", "Passenger added successfully.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        

        return "redirect:/admin/passenger-list?registered=true";
    }
    
    @GetMapping("/passenger/delete/{id}")
    @PreAuthorize("hasAuthority('PASSENGER_DELETE')")
    public String deletePassenger(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	try {
    		userService.deletePassenger(id);
            redirectAttributes.addFlashAttribute("success", "Passenger in-activated successfully.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/passenger-list";
    }
    
    @GetMapping("/passenger/activate/{id}")
    @PreAuthorize("hasAuthority('PASSENGER_DELETE')")
    public String activatePassenger(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	try {
    		userService.activatePermission(id);
            redirectAttributes.addFlashAttribute("success", "Passenger activated successfully.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/passenger-list";
    }
    
    @GetMapping({"/passenger/edit/{id}","/passenger/profile/{id}"})
    @PreAuthorize("hasAuthority('PASSENGER_EDIT')")
    public String editPermission(@PathVariable Long id, Model model) {
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

            return "passenger-view";  // View name
        }
        model.addAttribute("error", "User not found");
        return "error";
    }
    
    @PostMapping("/passenger/profile/{id}")
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

            return "passenger-view";
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

            return "passenger-view";
        }
        userService.update(id, userDTO);
        return "redirect:/admin/passenger/profile/" + id;
    }
    
    @PostMapping("/passenger/profile/change-password/{id}")
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
            return "passenger-view"; // reload profile with errors
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords do not match!");
            return "passenger-view";
        }

        userService.updatePassword(id, dto.getNewPassword());

        return "redirect:/admin/passenger/profile/" + id + "?passwordChanged=true";
    }
    
    @GetMapping("/agent-list")
    @PreAuthorize("hasAuthority('AGENT_VIEW')")
    public String listAgents(Model model) {
        List<AgentDTO> agents = userService.getAllAgents();
        model.addAttribute("agents", agents);
        return "agent-list"; 
    }
    
    @GetMapping("/agent/delete/{id}")
    @PreAuthorize("hasAuthority('AGENT_DELETE')")
    public String deleteAgent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	try {
    		userService.deletePassenger(id);
            redirectAttributes.addFlashAttribute("success", "Agent in-activated successfully.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/agent-list";
    }
    
    @GetMapping("/agent/activate/{id}")
    @PreAuthorize("hasAuthority('AGENT_DELETE')")
    public String activateAgent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	try {
    		userService.activatePermission(id);
            redirectAttributes.addFlashAttribute("success", "Agent activated successfully.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/agent-list";
    }
    
    @GetMapping({"/agent/edit/{id}","/agent/profile/{id}"})
    @PreAuthorize("hasAuthority('AGENT_EDIT')")
    public String editAgent(@PathVariable Long id, Model model) {
    	Optional<AgentDTO> agent = userService.getAgentById(id);
        if (agent.isPresent()) {
        	
            model.addAttribute("agent", agent.get());
            List<City> cities = cityService.getAllCities();
            List<String> states = cityService.getAllStates();

            model.addAttribute("cities", cities);
            model.addAttribute("states", states);
            
            ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
            changePasswordDTO.setId(id);
            model.addAttribute("changePasswordDTO", changePasswordDTO);

            return "agent-view";  
        }
        model.addAttribute("error", "User not found");
        return "error";
    }
    
    @PostMapping("/agent/profile/{id}")
    @PreAuthorize("hasAuthority('AGENT_EDIT')")
    public String updateAgentProfile(@PathVariable Long id,
                                     @Valid @ModelAttribute("agent") AgentDTO agentDTO,
                                     BindingResult result,
                                     Model model) {
    	Optional<UserDTO> existingUserOpt = userService.findById(id);

        if (existingUserOpt.isPresent()) {
            model.addAttribute("userdetail", existingUserOpt.get());
        }

        if (result.hasErrors()) {
            List<City> cities = cityService.getAllCities();
            List<String> states = cityService.getAllStates();

            model.addAttribute("cities", cities);
            model.addAttribute("states", states);
            
            ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
            changePasswordDTO.setId(id);
            model.addAttribute("changePasswordDTO", changePasswordDTO);

            return "agent-view";
        }

        UserDTO existingUser = existingUserOpt.get();

        // Check if username changed
        if (!existingUser.getUsername().equals(agentDTO.getUsername())) {
            if (userService.usernameExists(agentDTO.getUsername())) {
                result.rejectValue("username", "error.agent", "Username already exists");
            }
        }

        // Check if phone number changed
        if (!existingUser.getPhoneNumber().equals(agentDTO.getPhoneNumber())) {
            if (userService.phoneNumberExists(agentDTO.getPhoneNumber())) {
                result.rejectValue("phoneNumber", "error.agent", "Phone number already exists");
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

            return "agent-view";
        }
        userService.updateAgent(id, agentDTO);
        return "redirect:/admin/agent/profile/" + id;
        
    }
    
    @PostMapping("/agent/profile/change-password/{id}")
    public String changeAgentPassword(@PathVariable Long id,
                                 @Valid @ModelAttribute("changePasswordDTO") ChangePasswordDTO dto,
                                 BindingResult result,
                                 Model model) {
System.out.println("------------------changeAgentPassword");
Optional<AgentDTO> agent = userService.getAgentById(id);
        if (agent.isPresent()) {
        	
            model.addAttribute("agent", agent.get());
            List<City> cities = cityService.getAllCities(); 
            List<String> states = cityService.getAllStates();

            model.addAttribute("cities", cities);
            model.addAttribute("states", states);
            
            ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
            changePasswordDTO.setId(id);
            model.addAttribute("changePasswordDTO", changePasswordDTO);
        }
        if (result.hasErrors()) {
            model.addAttribute("passwordError", "Please fill both fields correctly");
            return "agent-view"; // reload profile with errors
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords do not match!");
            return "agent-view";
        }

        userService.updatePassword(id, dto.getNewPassword());

        return "redirect:/admin/agent/profile/" + id + "?passwordChanged=true";
    }
    
    @GetMapping("/agent/register")
    @PreAuthorize("hasAuthority('AGENT_ADD')")
    public String showAgentSignUpForm(Model model) {
		model.addAttribute("agentDTO", new AgentRegisterDTO());
        model.addAttribute("states", cityService.getAllStates());
        model.addAttribute("cities", cityService.getAllCities());
        
        return "agent-add";
    }
    
    @PostMapping("/agent/register")
    public String registerAgentUser(@Valid @ModelAttribute("agentDTO") AgentRegisterDTO agentDTO, BindingResult result, Model model,RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
        	System.out.println("sadsd"+result);
        	List<City> cities = cityService.getAllCities();
            List<String> states = cityService.getAllStates();

            model.addAttribute("cities", cities);
            model.addAttribute("states", states);
            return "agent-add";
        }

        if (!agentDTO.getPassword().equals(agentDTO.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords do not match");
            model.addAttribute("states", cityService.getAllStates());
            model.addAttribute("cities", cityService.getAllCities());
            
            return "agent-add";
        }
        
        if (userService.usernameExists(agentDTO.getUsername())) {
            result.rejectValue("username", "error.registrationDTO", "Username already exists");
        }
        
        if (userService.phoneNumberExists(agentDTO.getPhoneNumber())) {
                result.rejectValue("phoneNumber", "error.registrationDTO", "Phone number already exists");
        }

        if (result.hasErrors()) {
            List<City> cities = cityService.getAllCities();
            List<String> states = cityService.getAllStates();

            model.addAttribute("cities", cities);
            model.addAttribute("states", states);
            return "agent-add";
        }

        try {
        	userService.saveAgent(agentDTO);
            redirectAttributes.addFlashAttribute("success", "Agent added successfully.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        

        return "redirect:/admin/agent-list?registered=true";
    }
}
