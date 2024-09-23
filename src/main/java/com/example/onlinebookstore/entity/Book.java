package com.example.onlinebookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Book"
//,uniqueConstraints = {@UniqueConstraint( columnNames = { "title" }) }
)
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(nullable = false)
	private Long id;

	@Column(nullable = false,unique=true)
	private String title;

	@Column(nullable = false)
	private String author;

	@Column(nullable = false)
	private Double price;


}
