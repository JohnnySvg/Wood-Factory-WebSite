package com.lemn.sitelemn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Product {
  @Id @GeneratedValue private Long id;
  private String name;
  private String description;
  private double price;
  private int stock;
  private String imageUrl;
  private String category;
  
  public Product() {
  }
  
  public Product(String name, String description, String category,  double price, int stock, String imageUrl) {
      this.name = name;
      this.description = description;
      this.price = price;
      this.stock = stock;
      this.imageUrl = imageUrl;
      this.category = category;
  }
  
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public int getStock() {
	return stock;
}
public void setStock(int stock) {
	this.stock = stock;
}
public String getImageUrl() {
	return imageUrl;
}
public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
  
  
  
}
