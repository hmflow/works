package com.ct.web.test;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleafTest")
public class ThymeleafTest {

	@GetMapping("/test001")
	public String getTestPage(Model mod) {
		mod.addAttribute("val", "test");
		return "/thymeleafTest/test001";
	}
	@GetMapping("/test002")
	public String getTestPage02(Model mod) {
		mod.addAttribute("val", "<b>test</b>");
		return "/thymeleafTest/test001";
	}
	
	@GetMapping("/testListData")
	public String getListTest01(Model mod){
		 
		return "/thymeleafTest/test001";
	}
}
