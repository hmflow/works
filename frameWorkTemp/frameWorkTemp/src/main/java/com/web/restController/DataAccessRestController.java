package com.web.restController;
 
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.service.DbConnecterService;

@RestController
public class DataAccessRestController{
	@Autowired
	private DbConnecterService dbConnService;
	 
	@PostMapping("/calldata")
	public Object callEvent(@RequestParam String sqlId
			             , @RequestParam String sqlCommand
			             , @RequestParam Map sqlparam ) { 
		Object returnObject = null; 
		if(sqlCommand.equals("listMap")) { 
			returnObject = dbConnService.getMapList(sqlId, sqlparam);
		}else if(sqlCommand.equals("mapRow")) {
			returnObject = dbConnService.getMapRow(sqlId, sqlparam);			
		}else if(sqlCommand.equals("count")) {
			returnObject = dbConnService.getCount(sqlId, sqlparam);
		}else {
			returnObject = new String("-99");
		} 
		System.err.println(returnObject); 
		return returnObject;
	}
}
