package com.badminton.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "Categories")
public class Category
{
  @Id
  @Column(name = "CATEGORYID")
  private int categoryId;
	
  @Column(name = "CATEGORYFULLNAME")
  private String category_full_name;

  @Column(name = "CATEGORYSHORTNAME")
  private String category_short_name;

public int getCategoryId() {
	return categoryId;
}

public void setCategoryId(int categoryId) {
	this.categoryId = categoryId;
}

public String getCategory_full_name() {
	return category_full_name;
}

public void setCategory_full_name(String category_full_name) {
	this.category_full_name = category_full_name;
}

public String getCategory_short_name() {
	return category_short_name;
}

public void setCategory_short_name(String category_short_name) {
	this.category_short_name = category_short_name;
}

  

}