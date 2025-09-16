package com.anil.swiftBus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.anil.swiftBus.dto.RolePermissionDTO;
import com.anil.swiftBus.entity.Permission;
import com.anil.swiftBus.entity.Role;
import com.anil.swiftBus.security.CustomUserDetailsService;
import com.anil.swiftBus.service.PermissionService;
import com.anil.swiftBus.service.RoleService;

@Controller
@RequestMapping("/roles")
public class RolePermissionController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @PreAuthorize("hasAuthority('ROLE_PERMISSION_MANAGE')")
    @GetMapping("/permissions")
    public String showRolePermissionPage(Model model) {
       

        List<Role> roles = roleService.findAll();
        List<Permission> permissions = permissionService.getAllPermissions();

        // Prepare map of roleId -> Set of permissionIds
        Map<Long, Set<Long>> rolePermissionMap = new HashMap<>();
        for (Role r : roles) {
            Set<Long> permissionIds = r.getRolePermissions().stream()
                                       .map(rp -> rp.getPermission().getId())
                                       .collect(Collectors.toSet());
            rolePermissionMap.put(r.getId(), permissionIds);
        }

        model.addAttribute("roles", roles);
        model.addAttribute("permissions", permissions);
        model.addAttribute("rolePermissionDTO", new RolePermissionDTO());
        model.addAttribute("rolePermissionMap", rolePermissionMap);

        return "roles-permission-list";
    }

    @PreAuthorize("hasAuthority('ROLE_PERMISSION_MANAGE')")
    @PostMapping("/save-permissions")
    public String saveRolePermissions(@ModelAttribute RolePermissionDTO rolePermissionDTO,
                                      RedirectAttributes redirectAttributes, Authentication authentication) {
        roleService.updateRolePermissions(rolePermissionDTO);
        String username = authentication.getName();
        UserDetails updatedUserDetails = userDetailsService.loadUserByUsername(username);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                authentication.getCredentials(),
                updatedUserDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(newAuth);
        redirectAttributes.addFlashAttribute("success", "Role permissions updated successfully!");
        return "redirect:/roles/permissions";
    }
    

	
}
