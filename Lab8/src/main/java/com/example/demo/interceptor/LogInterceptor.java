package com.example.demo.interceptor;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.entity.Account;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        
        if (user != null) {
            String uri = request.getRequestURI();
            String userFullname = user.getFullname();
            Date accessTime = new Date();

            System.out.println("LOG: URI=" + uri + ", Time=" + accessTime + ", User=" + userFullname);
        }
        
        return true;
    }
}
