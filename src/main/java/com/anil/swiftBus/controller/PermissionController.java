package com.anil.swiftBus.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.anil.swiftBus.dto.PermissionDTO;
import com.anil.swiftBus.entity.Permission;
import com.anil.swiftBus.service.PermissionService;

@Controller
@RequestMapping("/permissions")
public class PermissionController {
	
	@Autowired
    private PermissionService permissionService;

	@GetMapping({"/",""})
	@PreAuthorize("hasAuthority('PERMISSION_VIEW')")
    public String listPermissions(Model model) {
        model.addAttribute("permissions", permissionService.getAllPermissions());
        model.addAttribute("permission", new PermissionDTO()); 
        return "permission-list";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('PERMISSION_ADD')")
    public String addPermission( @Valid @ModelAttribute("permission") PermissionDTO dto,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (permissionService.existsByName(dto.getName())) {
            result.rejectValue("name", "error.permission", "Permission name must be unique");
        }

        if (result.hasErrors()) {
        	model.addAttribute("openModal", true);
            model.addAttribute("permissions", permissionService.getAllPermissions());
            return "permission-list"; // stay on same page
        }
        try {
        	permissionService.addPermission(dto);
    		redirectAttributes.addFlashAttribute("success", "Permission saved successfully!");
    		} catch (Exception e) {
    		redirectAttributes.addFlashAttribute("error", "Error adding permission: " + e.getMessage());
    		}
        
        return "redirect:/permissions";
    }

    @GetMapping("/edit/{id}")
    public String editPermission(@PathVariable Long id, Model model) {
        Permission permission = permissionService.getPermissionById(id);
        model.addAttribute("permission", permission);
        model.addAttribute("permissions", permissionService.getAllPermissions());
        return "permission-list";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('PERMISSION_EDIT')")
    public String updatePermission(@Valid @ModelAttribute("permission") Permission permission,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
    	
    	if (permissionService.existsByName(permission.getName())) {
            result.rejectValue("name", "error.permission", "Permission name must be unique");
        }
		if (result.hasErrors()) {
			model.addAttribute("openModalEdit", true);
            model.addAttribute("permissions", permissionService.getAllPermissions());
            return "permission-list";
		}
		
		try {
		permissionService.updatePermission(permission);
		redirectAttributes.addFlashAttribute("success", "Permission updated successfully!");
		} catch (Exception e) {
		redirectAttributes.addFlashAttribute("error", "Error updating permission: " + e.getMessage());
		}
        return "redirect:/permissions";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    public String deletePermission(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	try {
            permissionService.deletePermission(id);
            redirectAttributes.addFlashAttribute("success", "Permission deleted successfully.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/permissions";
    }
}
