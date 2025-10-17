package com.example.demo.controller;

import com.example.demo.dao.CategoryDAO;
import com.example.demo.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryDAO dao;

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("item", new Category());
        model.addAttribute("items", dao.findAll());
        return "category/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") String id) {
        Optional<Category> optionalItem = dao.findById(id);
        if (optionalItem.isPresent()) {
            model.addAttribute("item", optionalItem.get());
        } else {
            // Handle case where item is not found, redirect to index
            model.addAttribute("item", new Category());
        }
        model.addAttribute("items", dao.findAll());
        return "category/index";
    }

    @RequestMapping("/create")
    public String create(@ModelAttribute Category item) {
        dao.save(item);
        return "redirect:/category/index";
    }

    @RequestMapping("/update")
    public String update(@ModelAttribute Category item) {
        dao.save(item);
        return "redirect:/category/edit/" + item.getId();
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        dao.deleteById(id);
        return "redirect:/category/index";
    }
}