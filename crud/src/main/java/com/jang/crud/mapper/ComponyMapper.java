package com.jang.crud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.jang.crud.vo.Compony;

@Mapper
public interface ComponyMapper {

	@Insert("INSERT INTO COMPONY VALUES (COMPONY_SEQ.NEXTVAL,#{compony.name},#{compony.address})")
	public int insert(@Param("compony") Compony compony);
	
	@Select("select * from compony")
	@Results(id="componyMap", value={
		@Result(property = "name", column = "compony_name"),
		@Result(property = "address", column = "compony_address"),
		@Result(property = "id", column = "compony_id")
	})
	public List<Compony> findAll();
	
	@Select("select sysdate form dual")
	public String getTime();
	
	@Select("select count(*) from compony")
	public int getCnt();
	
	@Select("select * from compony where compony_id = #{id}")
	@ResultMap("componyMap")
	public Compony findById(@Param("id") int id);
}
