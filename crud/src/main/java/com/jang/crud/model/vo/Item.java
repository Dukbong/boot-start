package com.jang.crud.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	
	private Long id;
	private String itemName;
	private Integer price;
	private Integer quantity;

}