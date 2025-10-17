package com.example.demo.controller;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.ProductDAO;
import com.example.demo.entity.Product;
import com.example.demo.service.SessionService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Controller
public class ProductController {
    @Autowired
    ProductDAO dao;

    @RequestMapping("/product/search")
    public String search(Model model,
                         @RequestParam("min") Optional<Double> min,
                         @RequestParam("max") Optional<Double> max) {
        double minPrice = min.orElse(Double.MIN_VALUE);
        double maxPrice = max.orElse(Double.MAX_VALUE);
        List<Product> items = dao.findByPrice(minPrice, maxPrice);
        model.addAttribute("items", items);
        return "product/search";
    }
    
    @Autowired
    SessionService session;

    @RequestMapping("/product/search-and-page")
    public String searchAndPage(Model model,
                                 @RequestParam("keywords") Optional<String> kw,
                                 @RequestParam("p") Optional<Integer> p) {
        String kwords = kw.orElse("");
        int pageIndex = p.orElse(0);
        if (pageIndex < 0) pageIndex = 0;

        PageRequest pageable = PageRequest.of(pageIndex, 5);
        Page<Product> page = dao.findByKeywords("%" + kwords + "%", pageable);

        model.addAttribute("page", page);
        model.addAttribute("keywords", kwords);
        return "product/search-and-page";
    }


}


