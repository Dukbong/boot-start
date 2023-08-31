package com.jang.crud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.jang.crud.vo.Compony;
import com.jang.crud.vo.Employee;

@Mapper
public interface EmployeeMapper {

	@Insert("INSERT INTO Employee VALUES (employee_SEQ.NEXTVAL,#{employee.componyId},#{employee.name},#{employee.address})")
	public int insert(@Param("employee") Employee employee);
	
	@Select("select * from employee")
	@Results(id="employeeMap", value={
		@Result(property = "name", column = "employee_name"),
		@Result(property = "address", column = "employee_address"),
		@Result(property = "componyId", column = "compony_id"),
		@Result(property = "id", column = "employee_id")
	})
	public List<Employee> findAll();
	
	
	@Select("select * from employee where employee_id = #{id}")
	@ResultMap("employeeMap")
	public Employee findById(@Param("id") int id);
	
	@Select("select * from employee where compony_id = #{componyId}")
	@ResultMap("employeeMap")
	List<Employee> getByComponyId(@Param("componyId") int componyId);
}
