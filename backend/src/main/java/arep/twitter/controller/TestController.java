package arep.twitter.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping
    public String testEndpoint(@RequestBody String message) {
        return "Received: " + message;
    }

    @GetMapping
    public String test(){
        return "HOLA";
    }
}
