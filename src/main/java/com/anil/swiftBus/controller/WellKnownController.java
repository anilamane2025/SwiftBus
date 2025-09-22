package com.anil.swiftBus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WellKnownController {

    @RequestMapping("/.well-known/**")
    @ResponseBody
    public ResponseEntity<String> handleWellKnown() {
        return ResponseEntity.notFound().build();
    }
}
