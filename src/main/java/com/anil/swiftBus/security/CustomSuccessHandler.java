package com.anil.swiftBus.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.springframework.security.core.*;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectUrl = "/login?error=true"; // fallback

        for (GrantedAuthority authority : authorities) {
            switch (authority.getAuthority()) {
                case "ROLE_ADMIN":
                    redirectUrl = "/admin/home";
                    break;
                case "ROLE_AGENT":
                    redirectUrl = "/agent/home";
                    break;
                case "ROLE_USER":
                    redirectUrl = "/user/home";
                    break;
            }
        }

        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}
