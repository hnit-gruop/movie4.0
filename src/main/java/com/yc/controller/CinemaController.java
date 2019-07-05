package com.yc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CinemaController {
	
	
	
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("cinemaDetail")
	public String cinema(){
		
		return "pages/Cinema";
	}
}
