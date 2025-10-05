package com.anil.swiftBus.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws IOException, ServletException {
    	
    	String redirectUrl = request.getParameter("redirect");

        if (redirectUrl != null && !redirectUrl.isEmpty()) {
        	System.out.println("After login it is in CustomSuccessHandler redirect at "+redirectUrl);
            response.sendRedirect(redirectUrl);
            return;
        }
        

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        redirectUrl = "/login?error=true"; // fallback

        for (GrantedAuthority authority : authorities) {
            switch (authority.getAuthority()) {
                case "ROLE_ADMIN":
                	System.out.println("After login it is in CustomSuccessHandler..to redirect at /admin/home page..");
                    redirectUrl = "/admin/home";
                    break;
                case "ROLE_AGENT":
                	System.out.println("After login it is in CustomSuccessHandler..to redirect at /agent/home page..");
                    redirectUrl = "/agent/home";
                    break;
                case "ROLE_PASSENGER":
                	System.out.println("After login it is in CustomSuccessHandler..to redirect at /passenger/home page..");
                    redirectUrl = "/passenger/home";
                    break;
            }
        }

        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}
