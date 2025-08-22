package com.anil.swiftBus.controller;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/")
    public String Home() {
        return "index"; // index.jsp
    }

	@GetMapping("login")
    public String loginPage(HttpServletRequest request,
                            HttpServletResponse response,
                            Authentication authentication) throws IOException {

        // Prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            for (GrantedAuthority authority : authorities) {
                String role = authority.getAuthority();

                switch (role) {
                    case "ROLE_ADMIN":
                        response.sendRedirect(request.getContextPath() + "/admin/home");
                        return null;
                    case "ROLE_AGENT":
                        response.sendRedirect(request.getContextPath() + "/agent/home");
                        return null;
                    case "ROLE_USER":
                        response.sendRedirect(request.getContextPath() + "/user/home");
                        return null;
                }
            }
        }

        return "login";
    }
    
    @GetMapping("/admin/home")
    public String adminHome() {
        return "home"; 
    }
    
    @GetMapping("/agent/home")
    public String agentHome() {
        return "home";
    }

}