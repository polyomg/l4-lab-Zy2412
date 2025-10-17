package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.MailService;
import com.example.demo.service.MailService.Mail;

import jakarta.mail.MessagingException;

import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class MailController {

    @Autowired
    MailService mailService;
    
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @ResponseBody
    @RequestMapping("/mail/send")
    public String send() {
        try {
            // Thay "receiver@gmail.com" bằng email người nhận thực tế
            mailService.send("receiver@gmail.com", "Test Email", "<h1>Hello</h1>Đây là email thử nghiệm.");
            return "Mail của bạn đã được gửi đi";
        } catch (MessagingException e) {
            return e.getMessage();
        }
    }
    
    @ResponseBody
    @RequestMapping("/mail/queue") // Đổi URL để phân biệt
    public String queue() {
        mailService.push("receiver@gmail.com", "Scheduled Email", "<h1>Hello</h1>Đây là email được gửi từ hàng đợi.");
        return "Mail của bạn đã được xếp vào hàng đợi";
    }
    
    @GetMapping("/mail/form")
    public String mailForm(Model model) {
        model.addAttribute("mail", new Mail());
        return "mail-form";
    }

    @PostMapping("/mail/send")
    public String sendMail(Model model, Mail mail, @RequestParam("attachments") MultipartFile[] attachments) {
        String message;
        try {
            String fileNames = handleFileUpload(attachments);
            mail.setFilenames(fileNames);
            mailService.send(mail);
            message = "Thành công! Email đã được gửi trực tiếp.";
        } catch (MessagingException | IOException e) {
            message = "Lỗi! Không thể gửi email: " + e.getMessage();
        }
        model.addAttribute("message", message);
        return "mail-form";
    }

    @PostMapping("/mail/queue")
    public String queueMail(Model model, Mail mail, @RequestParam("attachments") MultipartFile[] attachments) {
        String message;
        try {
            String fileNames = handleFileUpload(attachments);
            mail.setFilenames(fileNames);
            mailService.push(mail);
            message = "Thành công! Email đã được xếp vào hàng đợi.";
        } catch (IOException e) {
            message = "Lỗi! Không thể xử lý tệp đính kèm: " + e.getMessage();
        }
        model.addAttribute("message", message);
        return "mail-form";
    }

    private String handleFileUpload(MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0) {
            return "";
        }
        
        File uploadDir = new File(UPLOAD_DIR);
        if(!uploadDir.exists()){
            uploadDir.mkdirs();
        }

        return Stream.of(files)
            .filter(file -> !file.isEmpty())
            .map(file -> {
                try {
                    Path path = Paths.get(UPLOAD_DIR + StringUtils.cleanPath(file.getOriginalFilename()));
                    Files.copy(file.getInputStream(), path);
                    return path.toString();
                } catch (IOException e) {
                    throw new RuntimeException("Không thể lưu file: " + file.getOriginalFilename(), e);
                }
            })
            .collect(Collectors.joining("; "));
    }
}
