package com.web.restController;
 
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.web.mapper.MybatisMapper;
import com.web.service.DbConnecterService; 
/**
 * 2023-11-20 JAVAWANG 
 * 데이터 호출을 위한 컨트롤러입니다.
 */
@RestController
public class DataAccessRestController{ 
	@Autowired
	private MybatisMapper mybatisMapper;
	
	// 매퍼 경로를 지정합니다.
	private final String MAPPER_DEFAULT = "com.web.mapperImplss.mappers.%sMapper.%s";
	private final String MES_ERR_NO_PRRAM = "%s 정보가 없습니다. \n";
	
    /**
     * 2023-11-20 JAVAWANG 
     * 데이터를 호출합니다.   
     * @param  Map sqlparam  - 파라메터를 받습니다.
     * @return ModelAndView - 페이지와 파라메터를 담아서 리턴합니다.
     * @throws ""
     */
	@RequestMapping(value = "/calldata", method = RequestMethod.POST) 
	public ResponseEntity<Object>  callEvent(@RequestBody Map<String, Object> sqlparam ) { 
		return new ResponseEntity<>(callSqlDataRepl(sqlparam), HttpStatus.OK);
	}
    /**
     * 2023-11-20 JAVAWANG 
     * sql을 호출합니다.
     * @param  Map sqlparam  - 파라메터를 받습니다.
     * @return ModelAndView - 페이지와 파라메터를 담아서 리턴합니다.
     * @throws ""
     */
	@RequestMapping(value = "/callsql", method = RequestMethod.POST) 
	public ResponseEntity<Object> callsql(@RequestBody Map<String, Object> sqlparam ) {
		System.err.println("아1111닌  ㅇㅇㅇ데");
		//반환용 ModelAndView 
		Map runParam = new HashMap();
		// 반환할 Parameter  
		// 쿼리의 head 파일을 지정합니다.
		String mapperHeadStr = "com.web.mapperImplss.util.UtilMapper.%s";

		Map qryData = (Map) mybatisMapper.selectDataSet(String.format(mapperHeadStr, "findQuery") ,sqlparam);
		  
		// 결과 값이 없을경우
		if(qryData == null) { 
			runParam.put("callParam",sqlparam );
			runParam.put("resultInt",-1 ); 
		}else {
			// 반환받은 파라메터에 파라메터를 병합합니다.
			sqlparam.putAll(qryData); 
			// 정적으로 입력되어지는 쿼리의 파라메터를 입력후 반환합니다
			runParam = queryUtil(sqlparam, (Map)sqlparam.get("parameters"));  
			runParam = callSqlDataRepl(runParam);
		} 
		return new ResponseEntity<>(runParam, HttpStatus.OK);
	}
	
    /**
     * 2023-11-28 JAVAWANG 
     * 입력되어진 쿼리가 정상 혹은 데이터 를 확인할수 있습니다.
     * @param  Map sqlparam  - 파라메터를 받습니다.
     * @return ModelAndView - 페이지와 파라메터를 담아서 리턴합니다.
     * @throws ""
     */
	@RequestMapping(value = "/callsqlTest", method = RequestMethod.POST) 
	public ResponseEntity<Object> callsqlTest(@RequestBody Map<String, Object> sqlparam ) {
		//반환하기위한 Map을 생성합니다
		Map runParam = new HashMap();
		// sql 정보를 호출하여 비교할수 있도록 저장합니다.
		String sqlStr = (String) sqlparam.get("sql"); 
		
		runParam.put("call", sqlStr);
		// 반환할 Parameter  
		// 쿼리의 head 파일을 지정합니다.
		String mapperHeadStr = "com.web.mapperImplss.util.UtilMapper.%s";
 		System.err.println("test");
		// 정적으로 입력되어지는 쿼리의 파라메터를 입력후 반환합니다
		Map data = queryUtil(sqlparam, (Map)sqlparam.get("parameters"));
		// 변환한 데이터를 저장합니다.
		runParam.put("change", data);
		// 호출할 타입을 가져와서 쿼리아이디를 지정합니다.
		String qryType = (String) sqlparam.get("type");
		String sqlidInfo = String.format(mapperHeadStr, qryType);
		// 쿼리 결과를 저장할 객체
		Object returnObject = null;
		// 처리상태를 저장할 객체
		int resultValue = 0;
		// 쿼리 타입은 select 와 update 두종류가 있습니다.
		// UTIL01 쿼리에 지정이 되어있습니다.
		switch (qryType) {
	        case "selectQueryCheck":
	        	// 조회쿼리를 호출후 반환합니다.
	            returnObject = mybatisMapper.selectDataList(sqlidInfo, sqlparam);
	            break;
	        case "updateQueryCheck":
	        	// 업데이트, 입력 쿼리등등을 실행여부만확인하고 반환합니다.
	            returnObject = mybatisMapper.updateQueryCheck(sqlidInfo, sqlparam);
	            break;
	        default:
	        	resultValue = -99;
	        	break;
		}
		runParam.put("resultInt", resultValue);
		runParam.put("result", returnObject); 
            
		return new ResponseEntity<>(runParam, HttpStatus.OK);
	}
    /**
     * 2023-11-20 JAVAWANG 
     * 마이바티스 쿼리를 파라메터를 입력후 반환합니다.
     * 
     * %% 파라메터가 다 없을경우 예외처리를 하지않았습니다.
     * @param  Map qryData  - 파라메터를 받습니다.
     * @return Map - 결과값 반환
     * @throws ""
     */
	private Map queryUtil(Map qryData, Map parameters) {
		// 쿼리를 호출합니다.
		String sql = (String) qryData.get("sql");
		// 파라메터 칸에 빈칸이 있을경우 삭제
		Pattern pattern = Pattern.compile("#\\{\\s*(\\w+)\\s*\\}");
        Matcher matcher = pattern.matcher(sql);
        StringBuffer qryStrBuffer = new StringBuffer();
        while(matcher.find()) {
        	String replacement = "#{" + matcher.group(1) + "}";
        	matcher.appendReplacement(qryStrBuffer, replacement);
        }
        matcher.appendTail(qryStrBuffer);
        // 파라메터칸에 빈칸이 삭제되어진것을 반영
        sql = qryStrBuffer.toString();
		// 쿼리에 입력되어진 파라메터를 실행할수록 수정합니
		for(Object key : parameters.keySet()) {
			// 쿼리에 파라메터를 변화합니다.
			sql = sql.replace(String.format("#{%s}", key), String.format("'%s'",parameters.get(key)));
		};  
		qryData.put("sql", sql);
		return qryData;
	}

    /**
     * 2023-11-27 JAVAWANG 
     * 서비스 호출 관리합니다.
     * @param  Map sqlparam  - 파라메터를 받습니다.
     * @return ModelAndView - 페이지와 파라메터를 담아서 리턴합니다.
     * @throws ""
     */
	private Map<String, Object> callSqlDataRepl(Map<String, Object> sqlparam) { 
		// 리턴할 객체를 정의 합니다.
		// 정의시 먼저 쿼리 정합성을 체크합니다.
		Map<String, Object> paramAndReturn = checkListValidate(sqlparam);
		
		// 파라메터가 정확히 입력되어있지 않을경우 에러를 반환합니다
		if(paramAndReturn.containsKey("errMessage")) {
			paramAndReturn.put("resultInt", -99);
			paramAndReturn.put("errMessage", paramAndReturn.get("errMessage")); 
			return paramAndReturn;
		}
		
		// sql커멘드를 받아옵니다.
		String sqlcommand = (String) paramAndReturn.get("sqlCommand"); 
		// sqlid 의 파라메터를 받아옵니다
		String sqlid = (String) paramAndReturn.get("sqlId");
		// 네임스페이스의 매퍼(클래스명) 을 가져옵니다.
		String mapper = (String) paramAndReturn.get("mapper");  
		// sql 경로를 조합니다.
		String sqlidInfo = String.format(MAPPER_DEFAULT, mapper, sqlid);
		
		// 작업이 완료 되었을경우 결과 값을 반화합니다.
	    int resultValue = 0;
	    // 쿼리 호출후 데이터를 담을 객체입니다.
	    Object returnObject = null;
	    try {
	    	// sqlcommand 기준으로 함수를 호출합니다.
	        Class<?> mybatisMapperClass = mybatisMapper.getClass();
 	        Method method = mybatisMapperClass.getMethod(sqlcommand, String.class, Object.class);
	        // 쿼리별로 조회합니다.
	        if(sqlcommand.startsWith("select")) {
	        	/** 조회*/
	        	returnObject = method.invoke(mybatisMapper, sqlidInfo, sqlparam.get("parameters"));
	        } else if(sqlcommand.startsWith("update") || sqlcommand.startsWith("insert")
	        	                                      || sqlcommand.startsWith("delete")) { 
	        	//DCL쿼리 실행후 성공 결과를 저장합니다.
	        	returnObject = method.invoke(mybatisMapper, sqlidInfo, sqlparam.get("parameters"));
	        } else {
	            resultValue = -99;
	        }
	   
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	resultValue = -99; 
	        paramAndReturn.put("exceptionMessage" , e.toString());
	    }
	    // 결과값을 저장합니다. 
	    paramAndReturn.put("resultData",returnObject);  
	    return paramAndReturn;
	}

	
	
	/**
     * 2023-11-28 JAVAWANG 
     * 파라메터 유효성 검사합니다. 
     * @throws ""
     */
	private Map<String, Object> checkListValidate(Map<String, Object> sqlparam){
		// 리할 벨류를 정의 합니다.
		Map<String, Object> returnValue = new HashMap<String, Object>();
		// sqlCommand,sqlId, mapper 파라메터를 각각 가져와서 정합한지 체크를 합니다
		List<String> paramList = Arrays.asList("sqlCommand","sqlId", "mapper");
		// 에러메세지를 저장할 임시공간
		String errMessage = "";
		// 파라메터 정합성 체크
		for(String str : paramList) {
			if(sqlparam.get(str) == null) {
				// 만약에 파라메터가 입력을 받지 않았을경우
				errMessage += String.format(MES_ERR_NO_PRRAM, str);
			}else {
				// 파라메어가 있을경우 리턴할 값에 저장하여 반환합니다.
				String paramStr =  (String) sqlparam.get(str);
				// 매퍼파라메터일경우매퍼명을 변경후 입력
				paramStr = str.equals("mapper") ? paramStr.toUpperCase().charAt(0) + paramStr.substring(1) : paramStr; 
				returnValue.put(str , paramStr);
			}
		}
		if(!"".equals(errMessage)) {
			// 에러메세지를 입력후 리턴
			returnValue.put("errMessage", errMessage);
		}		
		return returnValue;
	}

    /**
     * 2023-11-20 JAVAWANG 
     * 서비스 호출 관리합니다. (위에 구현한 내용이 문제가 있을경우 이로직으로 교체)
     * @param  Map sqlparam  - 파라메터를 받습니다.
     * @return ModelAndView - 페이지와 파라메터를 담아서 리턴합니다.
     * @throws ""
     */
	private ModelAndView callSqlData(Map sqlparam) {
		// 리턴할 ModelAndView 객체를 정의 합니다.
		ModelAndView mov = new ModelAndView();
		// sql커멘드를 받아옵니다.
		String sqlcommand = (String) sqlparam.get("sqlCommand"); 
		// sqlid 의 파라메터를 받아옵니다
		String sqlid = (String) sqlparam.get("sqlId");
		// 네임스페이스의 매퍼(클래스명) 을 가져옵니다.
		String mapperStr = (String) sqlparam.get("mapper");  
		// 리턴할 개겣를 정의 합니다.
		mapperStr = mapperStr.toUpperCase().charAt(0) + mapperStr.substring(1);
		// sql 경로를 조합니다.
		String sqlidInfo = String.format(MAPPER_DEFAULT, mapperStr,sqlid);
		// JSON 파일로 파라메터 호출
		List<Map> dataListUpdate =  new ArrayList<Map>();
		// 결과 값을 반환하는 맵입니다
		Map<String, Object> resultData = new HashMap<>();
		// 쿼리에 오류가 있을경우 -1 로 변환합니다.
		Object resultValue =  0;
		// 쿼리 호출후 데이터를 담을 객체입니다.
	    Object returnObject = null;
	    switch (sqlcommand) {
	        case "selectDataList":
	            returnObject = mybatisMapper.selectDataList(sqlidInfo, sqlparam);
	            break;
	        case "selectDataSet":
	            returnObject = mybatisMapper.selectDataSet(sqlidInfo, sqlparam);
	            break;
	        case "selectCount":
	            returnObject = mybatisMapper.selectCount(sqlidInfo, sqlparam);
	            break;
	        case "updateDataList":
	        	resultValue = mybatisMapper.updateDataList(sqlidInfo, dataListUpdate);
	            break;
	        case "updateDataSet":
	        	resultValue = mybatisMapper.updateDataSet(sqlidInfo, dataListUpdate);
	            break;
	        case "insertDataList":
	        	resultValue = mybatisMapper.insertDataList(sqlidInfo, dataListUpdate);
	            break;
	        case "insertDataSet":
	        	resultValue = mybatisMapper.insertDataSet(sqlidInfo, dataListUpdate); 
	            break;    
	        case "deleteDataSet":
	        	resultValue = mybatisMapper.deleteDataSet(sqlidInfo, dataListUpdate);
	            break;
	        case "deleteDataList":
	        	resultValue = mybatisMapper.deleteDataList(sqlidInfo, dataListUpdate);
	            break;  
	        default:
	        	resultValue = -99;
	    } 
	    // 결과값을 저장합니다.
        resultData.put("resultInt", String.valueOf(resultValue));
        resultData.put("resultData",returnObject);   
		return mov; 
		
	}
}
