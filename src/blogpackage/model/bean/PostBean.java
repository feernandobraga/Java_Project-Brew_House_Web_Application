package com.controller;

public class PostBean {
	private int postID;
	private String postTitle;
	private int postCategory;
	private String postDate;
	private int postAuthor;
	private String postContent;
	private boolean isPostVisable;
	
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public int getPostCategory() {
		return postCategory;
	}
	public void setPostCategory(int postCategory) {
		this.postCategory = postCategory;
	}
	public String getPostDate() {
		return postDate;
	}
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	public int getPostAuthor() {
		return postAuthor;
	}
	public void setPostAuthor(int postAuthor) {
		this.postAuthor = postAuthor;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public boolean isPostVisable() {
		return isPostVisable;
	}
	public void setPostVisable(boolean isPostVisable) {
		this.isPostVisable = isPostVisable;
	}
	
}
