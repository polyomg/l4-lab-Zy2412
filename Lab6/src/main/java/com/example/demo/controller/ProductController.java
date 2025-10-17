package com.example.demo.controller;

import com.example.demo.dao.CategoryDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductDAO dao;

    @Autowired
    CategoryDAO cdao;

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("item", new Product());
        model.addAttribute("items", dao.findAll());
        model.addAttribute("categories", cdao.findAll()); // Để đổ dữ liệu cho dropdown category
        return "product/index";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        Optional<Product> optionalItem = dao.findById(id);
        if (optionalItem.isPresent()) {
            model.addAttribute("item", optionalItem.get());
        } else {
            model.addAttribute("item", new Product());
        }
        model.addAttribute("items", dao.findAll());
        model.addAttribute("categories", cdao.findAll()); // Để đổ dữ liệu cho dropdown category
        return "product/index";
    }

    @RequestMapping("/create")
    public String create(@ModelAttribute Product item) {
        dao.save(item);
        return "redirect:/product/index";
    }

    @RequestMapping("/update")
    public String update(@ModelAttribute Product item) {
        dao.save(item);
        return "redirect:/product/edit/" + item.getId();
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        dao.deleteById(id);
        return "redirect:/product/index";
    }
}