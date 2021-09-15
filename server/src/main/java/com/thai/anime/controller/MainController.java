package com.thai.anime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

//    @GetMapping("/fragment")
//    public String getNavBar() {
//        return "fragment/navbar";
//    }
}
