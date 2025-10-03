package com.anil.swiftBus.controller;

//imports (Boot 2.x/Tomcat 9)
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anil.swiftBus.service.SmsService;

//Boot 3.x/Tomcat 10 हो तो ऊपर वाले javax.* को jakarta.* कर दें

@RestController
@RequestMapping("/otp")
public class OtpController {

 private final SmsService smsService;

 public OtpController(SmsService smsService) {
     this.smsService = smsService;
 }

 @PostMapping("/send")
 public ResponseEntity<?> send(@RequestParam String phone, HttpSession session) {
     // 6-digit numeric OTP
     String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

     // session में सुरक्षित रखो (expiry के साथ)
     session.setAttribute("OTP_PHONE", phone);
     session.setAttribute("OTP_CODE", otp);
     session.setAttribute("OTP_VERIFIED", false);
     session.setAttribute("OTP_EXPIRES_AT", System.currentTimeMillis() + 5 * 60 * 1000); // 5 min

     // SMS भेजो (अभी console पर भी चल जाएगा)
     smsService.send(phone, "Your SwiftBus OTP is " + otp + " (valid 5 min)");

     return ResponseEntity.ok().body("{\"status\":\"OTP_SENT\"}");
 }

 @PostMapping("/verify")
 public ResponseEntity<?> verify(@RequestParam String otp, HttpSession session) {
     String savedOtp   = (String) session.getAttribute("OTP_CODE");
     Long   expiresAt  = (Long)   session.getAttribute("OTP_EXPIRES_AT");
     if (savedOtp == null || expiresAt == null) {
         return ResponseEntity.badRequest().body("{\"status\":\"NO_OTP\"}");
     }
     if (System.currentTimeMillis() > expiresAt) {
         return ResponseEntity.badRequest().body("{\"status\":\"EXPIRED\"}");
     }
     if (!savedOtp.equals(otp)) {
         return ResponseEntity.badRequest().body("{\"status\":\"INVALID\"}");
     }

     session.setAttribute("OTP_VERIFIED", true);
     // चाहें तो code मिटा दें ताकि re-use न हो
     session.removeAttribute("OTP_CODE");
     session.removeAttribute("OTP_EXPIRES_AT");

     return ResponseEntity.ok().body("{\"status\":\"VERIFIED\"}");
 }
}