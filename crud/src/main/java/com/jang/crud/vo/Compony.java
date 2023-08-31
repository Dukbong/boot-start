package com.jang.crud.vo;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Compony {
	private int id;
	private String name;
	private String address;
	
	private List<Employee> employeeList;
}
