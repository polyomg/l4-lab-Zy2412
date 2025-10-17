package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "home/index";
    }

    @GetMapping("/about")
    public String about() {
        return "home/about";
    }
    
//    @GetMapping("/reset-lang")
//    public String resetLang(HttpServletResponse response) {
//        Cookie cookie = new Cookie("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE", "");
//        cookie.setPath("/");
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//        return "redirect:/home/index";
//    }

}

