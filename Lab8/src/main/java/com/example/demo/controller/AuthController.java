package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;

import jakarta.servlet.http.HttpSession;


@Controller
public class AuthController {
    @Autowired
    AccountService accountService;
    @Autowired
    HttpSession session;

    @GetMapping("/auth/login")
    public String loginForm(Model model) {
        return "/auth/login";
    }

    @PostMapping("/auth/login")
    public String loginProcess(Model model,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password) {
        Account user = accountService.findById(username);
        if (user == null) {
            model.addAttribute("message", "Invalid username!");
        } else if (!password.equals(user.getPassword())) {
            model.addAttribute("message", "Invalid password!");
        } else {
            session.setAttribute("user", user);
            String securityUri = (String) session.getAttribute("securityUri");
            if (securityUri != null && !securityUri.isEmpty()) {
                return "redirect:" + securityUri;
            } else {
                 model.addAttribute("message", "Login successfully!");
            }
        }
        return "/auth/login";
    }
}
