package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Product;
import com.example.demo.report.Report;

public interface ProductDAO extends JpaRepository<Product, Integer> {

    // Bài 1: JPQL custom query
    @Query("FROM Product o WHERE o.price BETWEEN ?1 AND ?2")
    List<Product> findByPrice(double minPrice, double maxPrice);

    // Bài 2: JPQL custom query with paging
    @Query("FROM Product o WHERE o.name LIKE ?1")
    Page<Product> findByKeywords(String keywords, Pageable pageable);

    // Bài 3: Thống kê dữ liệu dạng interface projection
    @Query("SELECT o.category.name AS categoryName, SUM(o.price) AS sum, COUNT(o) AS count " +
    	       "FROM Product o GROUP BY o.category.name ORDER BY SUM(o.price) DESC")
    	List<Report> getInventoryByCategory();


    // Bài 4: DSL (Spring Data derived query)
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    // Bài 5: DSL + paging
    Page<Product> findAllByNameLike(String keywords, Pageable pageable);
}
