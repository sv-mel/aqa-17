package com.example.aqa.endpoints;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleEndpoint {

    @GetMapping(path = "/getString")
    public String getString() {
        return "Hello world! You're so cool!";
    }
}