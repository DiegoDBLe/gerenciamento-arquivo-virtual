package com.essia.vfs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class VueController {

    @GetMapping("/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/index.html";
    }
    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }

}
