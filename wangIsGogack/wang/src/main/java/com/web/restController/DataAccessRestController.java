package com.web.restController;
 
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.web.service.DbConnecterService;
 
@RestController
public class DataAccessRestController{
	@Autowired
	private DbConnecterService dbConnService;
    /**
     * 2023-11-20
     * 데이터를 호출합니다.   
     * @param  Map sqlparam  - 파라메터를 받습니다.
     * @return ModelAndView - 페이지와 파라메터를 담아서 리턴합니다.
     * @throws ""
     */
	@RequestMapping(value = "/calldata", method = RequestMethod.POST) 
	public ModelAndView callEvent(@RequestParam Map sqlparam ) {
		ModelAndView mov =callSqlData(sqlparam);
		return mov;
	}
    /**
     * 2023-11-20 
     * sql을 호출합니다.
     * @param  Map sqlparam  - 파라메터를 받습니다.
     * @return ModelAndView - 페이지와 파라메터를 담아서 리턴합니다.
     * @throws ""
     */
	@RequestMapping(value = "/callsql", method = RequestMethod.POST) 
	public ModelAndView callsql(@RequestParam Map sqlparam ) {
		Map qryData = dbConnService.selectDataSet("findQuery", sqlparam);	
		qryData.putAll(sqlparam);
		ModelAndView mov =callSqlData(queryUtil(qryData));
		return mov;
	}
    /**
     * 2023-11-20 
     * 마이바티스 쿼리를 파라메터를 입력후 반환합니다.
     * 
     * %% 파라메터가 다 없을경우 예외처리를 하지않았습니다.
     * @param  Map qryData  - 파라메터를 받습니다.
     * @return Map - 결과값 반환
     * @throws ""
     */
	private Map queryUtil(Map qryData) {
		System.err.println(qryData.get("SQL"));
		qryData.keySet().forEach(key->{
			String sql = (String) qryData.get("SQL");
			sql = sql.replace(String.format("#{%s}", key), String.format("'%s'",qryData.get(key)));
			qryData.put("SQL", sql);
		}); 
		qryData.put("sqlid", "inputcmd" );
		System.err.println(qryData.get("SQL"));
		return qryData;
	}
    /**
     * 2023-11-20  
     * 서비스 호출 관리합니다.
     * @param  Map sqlparam  - 파라메터를 받습니다.
     * @return ModelAndView - 페이지와 파라메터를 담아서 리턴합니다.
     * @throws ""
     */
	private ModelAndView callSqlData(Map sqlparam) {
		ModelAndView mov = new ModelAndView();
		String sqlcommand = (String) sqlparam.get("sqlcommand");
		String sqlid = (String) sqlparam.get("sqlid"); 
		
		Object returnObject = null;     
		if(sqlcommand.equals("selectDataSetList")) { 
			returnObject = dbConnService.selectDataSetList(sqlid, sqlparam);
		}else if(sqlcommand.equals("selectDataSet")) {
			returnObject = dbConnService.selectDataSet(sqlid, sqlparam);			
		}else if(sqlcommand.equals("selectCount")) {
			returnObject = dbConnService.selectCount(sqlid, sqlparam);
		}else {
			returnObject = new String("-99");  
		} 
		System.err.println(returnObject);  
		return mov; 
		
	}
}
