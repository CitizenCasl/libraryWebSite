package com.sgutsev.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library")
public class LibraryController {
    @GetMapping("/home")
    public String index(){
        return "startPage";
    }
}
