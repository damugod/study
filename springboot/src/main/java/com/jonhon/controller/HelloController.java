package com.jonhon.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {

    @Autowired
    private TestService testService ;
    @GetMapping("/{id}")
    public String sayHello(@PathVariable("id") String id) {
        log.info("Hello, Spring Boot!");
        try {
            testService.async(id);
        } catch (InterruptedException e) {
            log.error("e{}",e);
        }
        return "Hello, Spring Boot!";
    }
}
