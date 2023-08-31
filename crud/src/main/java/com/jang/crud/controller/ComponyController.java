package com.jang.crud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jang.crud.mapper.ComponyMapper;
import com.jang.crud.service.ComponyService;
import com.jang.crud.vo.Compony;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/compony")
@RequiredArgsConstructor
public class ComponyController {

	private final ComponyMapper componyMapper;
	private final ComponyService componyService;
	
	@PostMapping("")
	public int insert(@RequestBody Compony compony) {
		return componyMapper.insert(compony);
	}
	
	@GetMapping("/{id}")
	public Compony findById(@PathVariable("id") int id) {
		return componyMapper.findById(id);
	}
	
	@GetMapping("")
	public List<Compony> getAll(){
		return componyService.getAll();
	}
	
	@GetMapping("/all")
	public List<Compony> findAll() throws JsonProcessingException {
		List<Compony> lists = componyMapper.findAll();
		for(Compony com : lists) {
			System.out.println(com);
		}
		return lists;
	}
	
}
