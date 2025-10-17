package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Table(name = "Categories") // <- Chính xác tên bảng trong DB
public class Category {
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Id
    private String id;

    private String name;

    // Getters, setters
}
