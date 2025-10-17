package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;
    
    private final List<Mail> queue = new ArrayList<>();

    @Override
    public void push(Mail mail) {
        queue.add(mail);
    }

    @Scheduled(fixedDelay = 5000) // Chạy sau mỗi 5 giây
    public void run() {
        while (!queue.isEmpty()) {
            Mail mail = queue.remove(0);
            try {
                this.send(mail);
                System.out.println("Gửi mail thành công: " + mail.getTo());
            } catch (Exception e) {
                System.err.println("Lỗi gửi mail: " + e.getMessage());
                // Có thể thêm logic để push mail lỗi vào lại hàng đợi
            }
        }
    }

    private boolean isNullOrEmpty(String text) {
        return (text == null || text.trim().isEmpty());
    }

    @Override
    public void send(Mail mail) throws MessagingException {
        // 1. Tạo Mail
        MimeMessage message = mailSender.createMimeMessage();
        
        // 2. Tạo đối tượng hỗ trợ ghi nội dung Mail
        // true -> cho phép multipart (đính kèm file, html)
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        // 2.1. Ghi thông tin người gửi
        helper.setFrom(mail.getFrom());
        helper.setReplyTo(mail.getFrom());

        // 2.2. Ghi thông tin người nhận
        helper.setTo(mail.getTo());
        if (!this.isNullOrEmpty(mail.getCc())) {
            helper.setCc(mail.getCc().split("[,;\\s]+"));
        }
        if (!this.isNullOrEmpty(mail.getBcc())) {
            helper.setBcc(mail.getBcc().split("[,;\\s]+"));
        }

        // 2.3. Ghi tiêu đề và nội dung
        helper.setSubject(mail.getSubject());
        // true -> cho phép nội dung là HTML
        helper.setText(mail.getBody(), true);

        // 2.4. Đính kèm file
        String filenames = mail.getFilenames();
        if (!this.isNullOrEmpty(filenames)) {
            for (String filename : filenames.split("[,;]+")) {
                File file = new File(filename.trim());
                if(file.exists()){
                   helper.addAttachment(file.getName(), file);
                }
            }
        }

        // 3. Gửi Mail
        mailSender.send(message);
    }
}
