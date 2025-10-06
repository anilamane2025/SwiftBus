package com.anil.swiftBus.controller;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
    public String loginPage(HttpServletRequest request,
                            HttpServletResponse response,
                            Authentication authentication) throws IOException {
		System.out.println("Login Controller...");
        // Prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        if (authentication != null && authentication.isAuthenticated()) {
        	System.out.println("in the if condition of Login Controller class...");
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            for (GrantedAuthority authority : authorities) {
                String role = authority.getAuthority();
                System.out.println("printing the role : "+role);	
                switch (role) {
                    case "ROLE_ADMIN":
                    	System.out.println("since I have logged in as ADMIN redirecting to admin home page");
                        response.sendRedirect(request.getContextPath() + "/admin/home");
                        return null;
                    case "ROLE_AGENT":
                    	System.out.println("since I have logged in as AGENT redirecting to agent home page");
                        response.sendRedirect(request.getContextPath() + "/agent/home");
                        return null;
                    case "ROLE_PASSENGER":
                    	System.out.println("since I have logged in as USER redirecting to user home page");
                        response.sendRedirect(request.getContextPath() + "/passenger/home");
                        return null;
                }
            }
        }

        return "login";
    }
	
	@GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request,
                            HttpServletResponse response,
                            Authentication authentication) throws IOException {
		// Prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        if (authentication != null && authentication.isAuthenticated()) {
        	System.out.println("in the if condition of Login Controller class...");
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            for (GrantedAuthority authority : authorities) {
                String role = authority.getAuthority();
                System.out.println("printing the role : "+role);	
                switch (role) {
                    case "ROLE_ADMIN":
                    	System.out.println("since I have logged in as ADMIN redirecting to admin home page");
                        response.sendRedirect(request.getContextPath() + "/admin/home");
                        return null;
                    case "ROLE_AGENT":
                    	System.out.println("since I have logged in as AGENT redirecting to agent home page");
                        response.sendRedirect(request.getContextPath() + "/agent/home");
                        return null;
                    case "ROLE_PASSENGER":
                    	System.out.println("since I have logged in as USER redirecting to user home page");
                        response.sendRedirect(request.getContextPath() + "/passenger/home");
                        return null;
                }
            }
        }

        return "login";
    }
	
	@RequestMapping("/access-denied")
    public String accessDenied() {
		System.out.println("returning acces denied..");
        return "access-denied"; 
    }

}