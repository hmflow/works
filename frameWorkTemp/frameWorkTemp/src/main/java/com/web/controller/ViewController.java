package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {

    @GetMapping("/{page}")
    public String viewController(@PathVariable("page") String page) {
    	System.err.println(page + "this");
    	return page;
    } 
    @GetMapping("/{category1}/{page}")
    public String viewController(@PathVariable("category1") String category1
    		                    ,@PathVariable("page") String page) {
    	
    	return category1 + "/"  + page;
    } 
    @GetMapping("/{category1}/{category2}/{page}")
    public String viewController(@PathVariable("category1") String category1
                                ,@PathVariable("category2") String category2
                                ,@PathVariable("page") String page) {
    	return page;
    } 
    
    
}
