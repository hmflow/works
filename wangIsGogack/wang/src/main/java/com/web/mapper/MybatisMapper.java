package com.web.mapper;

import java.util.List; 
import java.util.Map; 
import com.web.util.DataSet;
 
 /*
  * 마이바티스 기본 매퍼 입니다.
  * 기본적으로 리스트를 받는 맵과
  * */
public interface MybatisMapper {
	public List<Map> selectDataSetList(String queryId, Map param); 
	public Map selectDataSet(String queryId, Map param);
	public int selectCount(String queryId, Map param123);
}   
