package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.model.DB;
import com.example.demo.model.User;
import com.example.demo.service.CookieService;
import com.example.demo.service.ParamService;
import com.example.demo.service.SessionService;
import java.io.File;

@Controller
public class AccountController {

    @Autowired
    ParamService paramService;

    @Autowired
    CookieService cookieService;

    @Autowired
    SessionService sessionService;

    // Hiển thị form đăng nhập
    @GetMapping("/account/login")
    public String login1(Model model) {
        String username = cookieService.getValue("user");
        model.addAttribute("username", username);
        return "account/login";
    }

    // Xử lý logic đăng nhập
    @PostMapping("/account/login")
    public String login2(Model model) {
        String username = paramService.getString("username", "");
        String password = paramService.getString("password", "");
        boolean remember = paramService.getBoolean("remember", false);

        User user = DB.users.get(username);

        if (user == null) {
            model.addAttribute("message", "Chưa có tài khoản. Vui lòng đăng ký.");
            return "account/login";
        }
        
        if (user.getPassword().equals(password)) {
            sessionService.set("username", username);
            if (remember) {
                cookieService.add("user", username, 240);
            } else {
                cookieService.remove("user");
            }
            return "redirect:/item/index";
        }
        
        model.addAttribute("message", "Mật khẩu không đúng!");
        return "account/login";
    }

    // Hiển thị form đăng ký
    @GetMapping("/account/register")
    public String register1() {
        return "account/register";
    }

    // Xử lý logic đăng ký và upload hình ảnh
    @PostMapping("/account/register")
    public String register2(
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("photo") MultipartFile photo,
        Model model
    ) {
        try {
            if (username.isEmpty() || password.isEmpty() || photo.isEmpty()) {
                model.addAttribute("message", "Vui lòng điền đầy đủ thông tin.");
                return "account/register";
            }

            if (DB.users.containsKey(username)) {
                model.addAttribute("message", "Username đã tồn tại. Vui lòng chọn username khác.");
                return "account/register";
            }
            
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);

            String uploadPath = "/images/users/";
            File savedFile = paramService.save(photo, uploadPath);
            newUser.setPhoto(savedFile.getName());
            model.addAttribute("uploadedPhoto", savedFile.getName());

            DB.users.put(username, newUser);
            cookieService.add("user", username, 240);
            
            return "redirect:/account/register?registered=true";

        } catch (RuntimeException e) {
            model.addAttribute("message", "Lỗi trong quá trình đăng ký: " + e.getMessage());
        }
        
        model.addAttribute("username", username);
        return "account/register";
    }
}