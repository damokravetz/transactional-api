package edu.palermo.transactionalapi.controllers;

import java.util.concurrent.atomic.AtomicLong;

import edu.palermo.transactionalapi.models.Greeting;
import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @PostMapping("/greeting")
    public Integer Post(@RequestBody Greeting myGreeting) {
        System.out.println(myGreeting.getContent());
        return 200;
    }

}