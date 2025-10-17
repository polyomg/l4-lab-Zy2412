package poly.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    // Hiển thị form login
    @GetMapping("/login/form")
    public String form() {
        return "poly/form"; // file login
    }

    // Xử lý login
    @PostMapping("/login/check")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        if ("poly".equals(username) && "123".equals(password)) {
            // Gửi dữ liệu sang home.html
            model.addAttribute("mssv", "TS01108");
            model.addAttribute("hoten", "Hồ Thảo Vy");
            return "poly/home"; // sang trang home
        } else {
            model.addAttribute("message", "Đăng nhập thất bại. Sai username hoặc password!");
            return "poly/form"; // quay lại login
        }
    }
}
