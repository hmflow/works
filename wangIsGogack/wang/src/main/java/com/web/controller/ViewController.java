package com.web.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 2023-11-20 JAVAWANG 
 * 
 * ViewController 에서 페이지이동을 관리합니다.   
 * 함수 viewController를 override 하여 구현하였고 카테고리2개 까지 만들었습니다.
 * 
 * 
 * --
 * modelAndView 에서 파라메터를 관리할지 확인
 * isValideMnteu 에서 사용자 권한을 관리합니다.
 */
@Controller
public class ViewController { 
	private static final Logger logger = LoggerFactory.getLogger(ViewController.class);
    /**
     * 2023-11-20 JAVAWANG 
     * 페이지를 호출합니다.   
     * @param  String page  - 페이지명
     * @return ModelAndView - 페이지와 파라메터를 담아서 리턴합니다.
     * @throws ""
     */
    @RequestMapping("/{page}") 
    public ModelAndView viewController(@PathVariable("page") String page, HttpSession  ss) {
        //getModelAndViewPageDirection 에 반환할 정보를 입력후 리턴합니다 
    	// ss.setAttribute("test" , "gggg");
    	System.err.println(  " <-bbbbbadsasdadsa 123123123     asdadsasdasd   asd  -- ");  
    	System.err.println(  " <-vvvv 123123123     asdadsasdasd   asd  -- ");  
    	System.err.println(  " <- 123123123     asdadsasdasd   asd  -- ");  System.err.println(  " <-bbbbbadsasdadsa 123123123     asdadsasdasd   asd  -- ");  
    	System.err.println(  " <-vvvv 123123123     asdadsasdasd   asd  -- ");  
    	System.err.println(  " <- 123123123     asdadsasdasd   asd  -- ");  
    	System.err.println(  " <-bbbbbadsasdadsa 123123123     asdadsasdasd   asd  -- ");  
    	System.err.println(  " <-vvvv 123123123     asdadsasdasd   asd  -- ");  
    	System.err.println(  " <- 123123123     asdadsasdasd   asd  -- ");  
    	
    	System.err.println(  " <-vvvv 123123123     asdadsasdasd   asd  -- ");  
    	System.err.println(  " <- 123123123     asdadsasdasd   asd  -- ");  
		return getModelAndViewPageDirection(page); 
    }	 
 
    /**
     * 2023-11-20 JAVAWANG 
     * 
     * (Override) 페이지를 호출합니다. 
     *    카테고리1개와 페이지명을 받아 반환합니다.
     * @param  String category1 - 카테고리명
     * @param  String page      - 페이지명
     * @return ModelAndView - 페이지와 파라메터를 담아서 리턴합니다.
     * @throws ""
     */
    @RequestMapping("/{category1}/{page}")
    public ModelAndView viewController(@PathVariable("category1") String category1
    		                          ,@PathVariable("page") String page) {
    	//getModelAndViewPageDirection 에 반환할 정보를 입력후 리턴합니다
		return getModelAndViewPageDirection(category1 + "/"  + page); 
    } 
    /**
     * 2023-11-20 JAVAWANG 
     * 
     * (Override) 페이지를 호출합니다. 
     *    카테고리2개와 페이지명을 받아 반환합니다.
     * @param  String category1 - 카테고리명
     * @param  String category2 - 카테고리명
     * @param  String page      - 페이지명
     * @return ModelAndView - 페이지와 파라메터를 담아서 리턴합니다.
     * @throws ""
     */
    @RequestMapping("/{category1}/{category2}/{page}")
    public ModelAndView viewController(@PathVariable("category1") String category1
                                      ,@PathVariable("category2") String category2
                                      ,@PathVariable("page") String page) { 
    	// getModelAndViewPageDirection 에 반환할 정보를 입력후 리턴합니다
    	return getModelAndViewPageDirection(category1 + "/"  +category2 + "/"  + page); 
    } 
    
    /**
     * 2023-11-20 JAVAWANG 
     * 
     * ModelAndView 를 통합하여 관리를 합니다.
     * @param String page - 이동할 페이지 위치
     * @return ModelAndView - 페이지와 파라메터를 담아서 리턴합니다.
     * @throws "" 
     */
    private ModelAndView getModelAndViewPageDirection(String page) {
    	// ModelAndView 생성
    	ModelAndView mv = new ModelAndView();
    	// 페이지명을 입력      
		mv.setViewName(page);
		return mv; 
    }


    /**
     * [미구현] 사용자 권한을 지정합니다.
     * @param String menuStr - 이동할 페이지 위치
     * @return String - 
     * @throws ""
     */
    private String isValideMnteu(String menuStr) {
    	return menuStr;
    }
	 
}
