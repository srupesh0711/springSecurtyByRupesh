package com.SSR.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/msg")
public class DumyController {
    // http://localhost:8080/api/v1/msg
    @PostMapping
    public String messgage(){
        return "Hello, this is a dummy controller!";
    }
}
