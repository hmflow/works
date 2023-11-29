package com.web.mapper;

import java.util.List; 
import java.util.Map; 
import com.web.util.DataSet;
 
/**
 * 2023-11-20 JAVAWANG 
 * 기초적으로 사용할 DDL, DML을 정의하여 구현할수 있도록 합니다.
 */
public interface MybatisMapper {
	public Object selectDataList(String queryId, Object param); 
	public Object selectDataSet(String queryId, Object param);
	public Object selectCount(String queryId, Object param);
	public Object updateDataList(String queryId, Object param);
	public Object updateDataSet(String queryId, Object param);
	public Object insertDataList(String queryId, Object param);
	public Object insertDataSet(String queryId, Object param);
	public Object deleteDataSet(String queryId, Object param);
	public Object deleteDataList(String queryId, Object param); 
	public Object selectQueryCheck(String queryId, Object param);
	public Object updateQueryCheck(String queryId, Object param);
}   
