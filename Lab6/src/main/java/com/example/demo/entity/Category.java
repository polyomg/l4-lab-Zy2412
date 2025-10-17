package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "Categories")
public class Category implements Serializable {
    @Id
    String id;
    String name;

    @ToString.Exclude // Thêm dòng này để tránh lỗi lặp vô hạn
    @OneToMany(mappedBy = "category")
    List<Product> products;
}