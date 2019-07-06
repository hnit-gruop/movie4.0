package com.yc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yc.bean.Cinema;
import com.yc.bean.Movie;
import com.yc.service.CinemaService;

@Controller
public class CinemaController {
	
	@Autowired
	CinemaService cinemaService;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("cinemaDetail")
	public String cinema(Model m,Integer cinemaId){
		Cinema cinema = cinemaService.cinema(cinemaId);
		m.addAttribute("c", cinema);
		System.out.println(cinema);
		return "pages/Cinema";
	}
	
	/**
	 * 影院列表 
	 */
	@RequestMapping("cinema")
	public String list(Model m) {
		
		List<Cinema> list = cinemaService.listCinema();
		m.addAttribute("cs", list);
		m.addAttribute("index", 3);
		return "pages/CinemaList";
	}
}
