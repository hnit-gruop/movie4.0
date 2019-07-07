package com.yc.controller;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.bean.Actor;
import com.yc.bean.Cinema;
import com.yc.bean.Movie;
import com.yc.bean.Schedule;
import com.yc.bean.Type;
import com.yc.service.CinemaService;
import com.yc.service.MovieService;
import com.yc.util.DateUtil;
import com.yc.util.DateUtils;

@Controller
public class CinemaController {
	
	@Autowired
	CinemaService cinemaService;
	
	@Autowired
	MovieService movieService;
	
	/**
	 * 影院详情
	 * @return
	 */
	@RequestMapping("cinemaDetail")
	public String cinema(Model m,Integer cinemaId){
		Cinema cinema = cinemaService.cinema(cinemaId);
		List<Movie> movieList = cinemaService.getMoiveList(cinemaId);
		
		if(movieList.size()>0) {
			int movieId = movieList.get(0).getMovieId();
			m.addAttribute("movieId", movieId);
		}
		
		m.addAttribute("movieList", movieList);
		m.addAttribute("c", cinema);
		m.addAttribute("index", 3);
		return "pages/Cinema";
	}
	
	
	
	/**
	 *  影片的详细场次信息
	 * @param m
	 * @param movieId
	 * @param cinemaId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("scheduleDetail")
	public Object getDetail(Model m,int movieId,int cinemaId) {
		Movie movie = movieService.getMovie(movieId);
		movieService.setActor(movie);
		movieService.setTypeName(movie);
		movieService.setScore(movie);
		
		
		
		List<Schedule> list = cinemaService.getSchedual(cinemaId,movieId);
		List<String> monthDayList = new ArrayList<>();
		
		
		/*日期格式处理*/
		for (Schedule schedule : list) {
			schedule.setShowStartTime(DateUtil.parseTimestampToStr(schedule.getStartTime(),"HH:mm"));
			schedule.setEndTime(new Timestamp(schedule.getStartTime().getTime()+movie.getDuration()*1000*60));
			schedule.setShowEndTime(DateUtil.parseTimestampToStr(schedule.getEndTime(),"HH:mm"));
			Integer month = DateUtil.getMonth(new Date(schedule.getStartTime().getTime()));
			Integer day = DateUtil.getDay(new Date(schedule.getStartTime().getTime()));
			
			String monthDay = month+"月"+day+"日";
			schedule.setMonthDay(monthDay);
			if(!monthDayList.contains(monthDay))
				monthDayList.add(monthDay);
		}
		Collections.sort(monthDayList);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("movie", movie);
		map.put("list", list);
		map.put("datelist", monthDayList);
		return map;
	}
	
	
	
	private Timestamp addTime(Timestamp startTime, Integer duration) {
		return null;
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
