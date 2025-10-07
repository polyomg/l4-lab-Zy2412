package com.example.demo.controller;

import com.example.demo.model.Staff;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

@Controller
public class StaffController {

    @RequestMapping("/staff/create/form")
    public String createForm(Model model) {
        model.addAttribute("staff", new Staff());
        model.addAttribute("message", "Vui lòng nhập thông tin nhân viên!");
        return "/demo/staff-create";
    }

    @RequestMapping("/staff/create/save")
    public String createSave(Model model,
    						 @RequestPart("photo_file") MultipartFile photoFile,
                             @Valid @ModelAttribute("staff") Staff staff,
                             Errors errors) {
        if (!photoFile.isEmpty()) {
            staff.setPhoto(photoFile.getName());
        }

        if (errors.hasErrors()) {
            model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
        } else {
            model.addAttribute("message", "Dữ liệu đã nhập đúng!");
        }

        model.addAttribute("staff", staff);
        return "/demo/staff-validate";
    }
}
