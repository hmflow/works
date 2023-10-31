package com.ct.web.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class CommonController {
	@GetMapping("/text")
	public String getText(Model modl) {
		System.err.println("test");
		return "common/text";
	}
	
	
}
