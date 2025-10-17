package poly.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import poly.edu.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController {

    // Danh sách sản phẩm (ban đầu có A, B + thêm nhiều sp giả)
    private List<Product> items = new ArrayList<>(Arrays.asList(
            new Product("A", 1.0),
            new Product("B", 12.0),
            new Product("C", 20.0),
            new Product("D", 30.0),
            new Product("E", 40.0),
            new Product("F", 50.0),
            new Product("G", 60.0),
            new Product("H", 70.0),
            new Product("I", 80.0),
            new Product("J", 90.0),
            new Product("K", 100.0),
            new Product("L", 110.0),
            new Product("M", 120.0),
            new Product("N", 130.0),
            new Product("O", 140.0),
            new Product("P", 150.0),
            new Product("Q", 160.0),
            new Product("R", 170.0),
            new Product("S", 180.0),
            new Product("T", 190.0)
    ));

    @ModelAttribute("p1")
    public Product defaultProduct() {
        return new Product("iPhone 30", 5000.0);
    }

    @GetMapping("/product/form")
    public String form(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        // Tính phân trang
        int totalItems = items.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        int start = (page - 1) * size;
        int end = Math.min(start + size, totalItems);

        List<Product> pageItems = items.subList(start, end);

        model.addAttribute("p2", new Product());
        model.addAttribute("items", pageItems);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", totalPages);

        return "poly/product_form";
    }

    @PostMapping("/product/save")
    public String save(@ModelAttribute("p2") Product p) {
        items.add(p);
        return "redirect:/product/form"; // redirect để load lại danh sách phân trang
    }
}
