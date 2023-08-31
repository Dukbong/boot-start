package com.jang.crud.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jang.crud.mapper.ComponyMapper;
import com.jang.crud.mapper.EmployeeMapper;
import com.jang.crud.vo.Compony;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComponyService {
	
	private final ComponyMapper componyMapper;
	private final EmployeeMapper employeeMapper;
	
	public List<Compony> getAll(){
		List<Compony> list = componyMapper.findAll();
		if(list != null && list.size() > 0) {
			list.stream()
				.forEach(co->{
					co.setEmployeeList(employeeMapper.getByComponyId(co.getId()));
				});
		}
		return list;
	}
}
