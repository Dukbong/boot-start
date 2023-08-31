package com.jang.crud.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ComponyMapperTest {

	
	private final ComponyMapper componyMapper;
	
	@Autowired
	ComponyMapperTest(ComponyMapper componyMapper){
		this.componyMapper = componyMapper;
	}
	
	@Test
	public void componyMapperTest() {
		int cnt = componyMapper.getCnt();
		Assertions.assertThat(cnt).isEqualTo(0);
	}
	
}
