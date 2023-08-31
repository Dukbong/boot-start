package com.jang.crud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jang.crud.mapper.EmployeeMapper;
import com.jang.crud.vo.Compony;
import com.jang.crud.vo.Employee;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

	private final EmployeeMapper employeeMapper;
	
	@PostMapping("")
	public int insert(@RequestBody Employee employee) {
		return employeeMapper.insert(employee);
	}
	
	@GetMapping("/{id}")
	public Employee findById(@PathVariable("id") int id) {
		return employeeMapper.findById(id);
	}
	
	@GetMapping("/all")
	public List<Employee> findAll() throws JsonProcessingException {
		List<Employee> lists = employeeMapper.findAll();
		lists.stream().forEach(m-> System.out.println(m));
		return lists;
	}
	
}
