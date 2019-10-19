package com.controller;

public class CategoryBean {
	private int catagoryID;
	private String categoryTitle;
	
	public CategoryBean() {

	}
	public void setCategoryID(int categoryID) {
		this.catagoryID = categoryID; 
	}
	
	public int getCatagoryID() {
		return catagoryID;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}
	
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}
}
