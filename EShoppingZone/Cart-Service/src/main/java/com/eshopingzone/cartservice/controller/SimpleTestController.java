package com.eshopingzone.cartservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleTestController {

    @GetMapping("/open-test")
    public String openTest() {
        System.out.println("Open test endpoint called successfully!");
        return "Open test endpoint working without any security checks";
    }
}
