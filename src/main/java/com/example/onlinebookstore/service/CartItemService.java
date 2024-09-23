package com.example.onlinebookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onlinebookstore.dto.CartItemDto;
import com.example.onlinebookstore.entity.Book;
import com.example.onlinebookstore.entity.CartItem;
import com.example.onlinebookstore.entity.User;
import com.example.onlinebookstore.repository.CartItemRepository;

@Service
public class CartItemService {
	@Autowired
	private CartItemRepository cartItemRepository;

	public List<CartItem> getCartItemsByUserId(Long userId) {
		return cartItemRepository.findByUserId(userId);
	}

	public CartItem addCartItem(CartItemDto cartItemDto) {
//    public CartItem addCartItem(Long userId, Long bookId, int quantity) {
		// Retrieve User and Book entities
		// User user = userRepository.findById(userId).orElseThrow(() -> new
		// ResourceNotFoundException("User not found"));
		// Book book = bookRepository.findById(bookId).orElseThrow(() -> new
		// ResourceNotFoundException("Book not found"));

		CartItem cartItem = new CartItem();

		User user = new User();
		user.setId(cartItemDto.getUserId());

		Book book = new Book();
		book.setId(cartItemDto.getBookId());

		cartItem.setUser(user);
		cartItem.setBook(book);
		cartItem.setQuantity(cartItemDto.getQuantity());

		CartItem cartItemNew = cartItemRepository.save(cartItem);
		return cartItemRepository.getOne(cartItemNew.getId());
	}

	public void deleteCartItem(Long id) {
		cartItemRepository.deleteById(id);
	}

	public void clearCartItemsByUserId(Long userId) {
		List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
		cartItemRepository.deleteAll(cartItems);
	}
}
