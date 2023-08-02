package ru.smartjava.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String checker() {
        return "Okay";
    }


}
