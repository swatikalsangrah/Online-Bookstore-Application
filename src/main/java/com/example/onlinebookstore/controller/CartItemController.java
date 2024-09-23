package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.CartItemDto;
import com.example.onlinebookstore.entity.CartItem;
import com.example.onlinebookstore.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartitems")
public class CartItemController {
	@Autowired
	private CartItemService cartItemService;

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping("/user/{userId}")
	public List<CartItem> getCartItemsByUserId(@PathVariable Long userId) {
		return cartItemService.getCartItemsByUserId(userId);
	}

    //@PostMapping
//    public CartItem addCartItem(@RequestBody CartItem cartItem) {
//        return cartItemService.addCartItem(cartItem);
//    }
	@PreAuthorize("hasRole('USER')")
	@PostMapping
//    public ResponseEntity<CartItem> addCartItem(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam int quantity) {
	public ResponseEntity<CartItem> addCartItem(@RequestBody CartItemDto cartItemDto) {
		CartItem cartItem = cartItemService.addCartItem(cartItemDto);
		return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
		cartItemService.deleteCartItem(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/user/{userId}/clear")
	public ResponseEntity<Void> clearCartItemsByUserId(@PathVariable Long userId) {
		cartItemService.clearCartItemsByUserId(userId);
		return ResponseEntity.noContent().build();
	}
}
