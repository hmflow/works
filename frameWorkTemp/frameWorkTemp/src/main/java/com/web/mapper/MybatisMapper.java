package com.web.mapper;
 

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
 

@Mapper
public interface MybatisMapper {
	public List<Map> getMapList(String queryId, Map param); 
	public Map getMapRow(String queryId, Map param);
	public int getCount(String queryId, Map param);
	
}

