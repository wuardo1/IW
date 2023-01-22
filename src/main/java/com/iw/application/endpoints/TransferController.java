package com.iw.application.endpoints;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AnonymousAllowed
public class TransferController {


    @GetMapping("/hello-world-controller")
    @AnonymousAllowed
    public String sayHello() {
        return "Hello";
    }
}
