package com.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.web.mapper.MybatisMapper;
 
 
/**
 * 2023-11-20 JAVAWANG 
 * 쿼리호출 관련 쿼리를 구현합니다.
 */
@Service
public class DbConnecterService implements  MybatisMapper{; 
	@Autowired 
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired 
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public Object selectDataList(String queryId, Object dataSet) { 
		return sqlSessionTemplate.selectList(queryId, dataSet);
	}

	@Override
	public Object selectDataSet(String queryId, Object dataSet) { 
		return sqlSessionTemplate.selectOne(queryId, dataSet);
	}

	@Override
	public Object selectCount(String queryId, Object dataSet) { 
		return sqlSessionTemplate.selectOne(queryId, dataSet);
	} 

	@Override
	public Object selectQueryCheck(String queryId, Object dataSet) { 
		return sqlSessionTemplate.selectList(queryId, dataSet);
	} 
	
	@Override
	public Object updateDataList(String queryId, Object dataList) { 
		return updateDatas(queryId, dataList, false);
	}

	@Override
	public Object updateDataSet(String queryId, Object dataSet) {
		return updateDatas(queryId, dataSet, false);
	}
 
	@Override
	public Object insertDataList(String queryId, Object dataList) {
	 	return updateDatas(queryId, dataList, false);
	}

	@Override
	public Object insertDataSet(String queryId, Object dataSet) { 
		return updateDatas(queryId, dataSet, false);
	}  
	
	@Override
	public Object deleteDataSet(String queryId, Object dataSet) { 
		return updateDatas(queryId, dataSet, false);
	}

	@Override
	public Object deleteDataList(String queryId, Object dataList) { 
		return updateDatas(queryId, dataList, false);
	}


	@Override
	public Object updateQueryCheck(String queryId, Object param) {
		return updateDatas(queryId, param, true);
	}

    /**
     * 2023-11-27 JAVAWANG 
     * 일괄 서비스호출을을 합니다.
     * @param  String    queryId  - 파라메터를 받습니다.
     * @return List<Map> dataList - 페이지와 파라메터를 담아서 리턴합니다.
     * @throws ""
     */
	private Object updateDatas(String queryId, Object dataObject, boolean isTest) {
		Map<String, Object> returnData = new HashMap<String, Object>();
		returnData.put("paramObj", dataObject); 
		int rsInt = 0;
		// SqlSession 을 구현하여 배치를 실행합니다.
		try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
			if(dataObject instanceof List) {
				// 파라메터 데이터가 리스트일경우 형변환을하여 각각 session 파라메터에 실행할 쿼리를 일괄로 입력합니다
				List<Map<String, Object>> dataList = (List<Map<String, Object>>) dataObject;
				for (Map dataSet : dataList) {
					sqlSession.update(queryId, dataSet); 
				}
			}else if(dataObject instanceof Map){
				// 파라메터가 단건일때 세션에 입력합니다.
				sqlSession.update(queryId, dataObject); 
			}else {
				// 입력되어진 파라메터가 없을경우 -1 을 리턴합니다.
				rsInt = -1;
			}
						
			if(isTest && rsInt ==0) {
				sqlSession.rollback();
			}else if(!isTest && rsInt ==0){
				// 쿼리를 실행후 적용합니다.
				sqlSession.flushStatements(); 
				sqlSession.commit();
			}
		}catch (Exception e) { 
			e.printStackTrace();
			rsInt = -1;
 			returnData.put("errMessage", e.toString());
		}
		// 결과값의 값을 저장합니다.
		returnData.put("resultInt", String.valueOf(rsInt));
		return returnData;
	}

	
}
  