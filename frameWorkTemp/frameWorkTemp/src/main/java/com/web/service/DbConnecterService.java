package com.web.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.web.mapper.MybatisMapper;
  

@Service
public class DbConnecterService implements  MybatisMapper{
	@Autowired 
	private SqlSession session;

	@Override
	public List<Map> getMapList(String queryId, Map param) {
		// TODO Auto-generated method stub
		return session.selectList(queryId, param);
	}

	@Override
	public Map getMapRow(String queryId, Map param) {
		// TODO Auto-generated method stub
		return session.selectOne(queryId, param);
	}

	@Override
	public int getCount(String queryId, Map param) {
		// TODO Auto-generated method stub
		return session.selectOne(queryId, param);
	}
}
 