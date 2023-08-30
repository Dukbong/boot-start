package com.jang.crud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CrudController {

	@GetMapping("/api/test/{id}")
	public void apiTest(@PathVariable("id") String id) {
		log.info("id = " + id);
		if(id.equals("ex")) {
			throw new IllegalArgumentException("test error!!");
		}
	}
}
