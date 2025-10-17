package com.example.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.entity.Account;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        Account user = (Account) session.getAttribute("user");
        String error = "";

        if (user == null) { // chưa đăng nhập
            error = "Please login!";
        } else if (!user.isAdmin() && uri.startsWith("/admin/")) { // không phải admin
            error = "Access denied!";
        }

        if (!error.isEmpty()) { // có lỗi
            session.setAttribute("securityUri", uri);
            response.sendRedirect("/auth/login?error=" + error);
            return false;
        }

        return true;
    }
}
