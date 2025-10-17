package poly.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HinhChuNhatController {

    // Hiển thị form
    @GetMapping("/rectangle/form")
    public String form() {
        return "poly/result";
    }

    // Xử lý khi submit form
    @PostMapping("/rectangle/calc")
    public String calculate(
            @RequestParam double length,
            @RequestParam double width,
            Model model) {

        // Nếu dài < rộng thì hoán đổi
        if (length < width) {
            double temp = length;
            length = width;
            width = temp;
        }

        double area = length * width;
        double perimeter = 2 * (length + width);

        // Công thức hiển thị
        String areaFormula = length + " × " + width + " = " + area;
        String perimeterFormula = "2 × (" + length + " + " + width + ") = " + perimeter;

        model.addAttribute("length", length);
        model.addAttribute("width", width);
        model.addAttribute("area", area);
        model.addAttribute("perimeter", perimeter);
        model.addAttribute("areaFormula", areaFormula);
        model.addAttribute("perimeterFormula", perimeterFormula);

        return "poly/result";
    }
}
