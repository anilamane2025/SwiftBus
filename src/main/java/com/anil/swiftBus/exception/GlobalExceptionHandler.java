package com.anil.swiftBus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Content type not supported. Please send a valid JSON request.");
    }
    
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ModelAndView handleAccessDenied(Exception ex) {
        ModelAndView mav = new ModelAndView("access-denied"); // JSP: /WEB-INF/views/access-denied.jsp
        mav.addObject("message", ex.getMessage());
        return mav;
    }

    // 500 - Any other exception
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception ex) {
        ModelAndView mav = new ModelAndView("error-500"); // JSP: /WEB-INF/views/error-500.jsp
        mav.addObject("message", ex.getMessage());
        return mav;
    }
}