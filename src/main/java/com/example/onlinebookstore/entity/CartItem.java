	package com.example.onlinebookstore.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_item", indexes = { @Index(name = "idx_user_id", columnList = "user_id"),
		                              @Index(name = "idx_book_id", columnList = "book_id") }, 
                           uniqueConstraints = {@UniqueConstraint(columnNames = { "user_id", "book_id" }) })

//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private int quantity;

//    @Column(name = "user_id", nullable = false, updatable = false)
//    private Long userId;
//
//    @Column(name = "book_id", nullable = false, updatable = false)
//    private Long bookId;

	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
	private Book book;
}
