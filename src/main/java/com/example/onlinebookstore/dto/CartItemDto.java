package com.example.onlinebookstore.dto;

import lombok.Data;

@Data
public class CartItemDto {

	private int quantity;
	private Long userId;
	private Long bookId;
	// private Book book;
	// private User user;
	private String title;
	private String author;
	private Double price;
}
