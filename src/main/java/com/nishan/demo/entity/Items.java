package com.nishan.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hotel_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private String name;
	@Lob
	private String image;
	
	@NotNull
	private String description;
	@NotNull
	private Integer quantity;
	@NotNull
	private Double price;
	
	private double TotalCost;
	private LocalDateTime createdDateTime;
	private LocalDateTime updatedDateTime;
	private boolean stock;
	
	@ManyToOne(targetEntity = Category.class,fetch = FetchType.EAGER)
	private Category category;
	
	@ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
	private User user;

}
