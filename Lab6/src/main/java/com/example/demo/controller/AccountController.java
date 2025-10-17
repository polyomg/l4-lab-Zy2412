package com.example.demo.controller;

import com.example.demo.dao.AccountDAO;
import com.example.demo.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountDAO dao;

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("item", new Account());
        model.addAttribute("items", dao.findAll());
        return "account/index";
    }

    @RequestMapping("/edit/{username}")
    public String edit(Model model, @PathVariable("username") String username) {
        Optional<Account> optionalItem = dao.findById(username);
        if (optionalItem.isPresent()) {
            model.addAttribute("item", optionalItem.get());
        } else {
            model.addAttribute("item", new Account());
        }
        model.addAttribute("items", dao.findAll());
        return "account/index";
    }

    @RequestMapping("/create")
    public String create(@ModelAttribute Account item) {
        dao.save(item);
        return "redirect:/account/index";
    }

    @RequestMapping("/update")
    public String update(@ModelAttribute Account item) {
        dao.save(item);
        return "redirect:/account/edit/" + item.getUsername();
    }

    @RequestMapping("/delete/{username}")
    public String delete(@PathVariable("username") String username) {
        dao.deleteById(username);
        return "redirect:/account/index";
    }
}