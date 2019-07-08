package com.yc.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yc.bean.Actor;
import com.yc.bean.Cinema;
import com.yc.bean.Hall;
import com.yc.bean.Movie;
import com.yc.bean.Schedule;
import com.yc.bean.Ticket;
import com.yc.bean.Type;
import com.yc.service.CinemaService;
import com.yc.service.MovieService;
import com.yc.service.ScheduleService;
import com.yc.service.TicketService;
import com.yc.util.DateUtil;
import com.yc.util.DateUtils;

@Controller
public class CinemaController {
	
	@Autowired
	CinemaService cinemaService;
	
	@Autowired
	MovieService movieService;
	
	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	TicketService ticketService;
	
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
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("movie", movie);
		map.put("list", list);
		map.put("datelist", monthDayList);
		return map;
	}
	
	
	
	/**
	 * 选座
	 */
	@RequestMapping("xseat")
	public String seat(Model m,int scheduleId,String cinemaName) {
		
		Schedule schedule = scheduleService.getScheduleByid(scheduleId);
		
		//电影基本信息
		Movie movie = movieService.getMovie(schedule.getMovieId());
		movieService.setCover(movie);
		movieService.setTypeName(movie);
		
		//场次基本信息
		Hall hall = cinemaService.getHallDetail(schedule.getHallId());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMdd日 HH:mm");
		String format = simpleDateFormat.format(schedule.getStartTime().getTime());
		schedule.setShowStartTime(format);
		
		
		//售过的座位
		List<Ticket> soldseats = ticketService.getSoldByScheduleId(schedule.getScheduleId());
		List<String> sold = new ArrayList<>();
		for (Ticket ticket : soldseats) {
			sold.add(ticket.getSeat());
		}
		
		
		m.addAttribute("sold", sold);
		m.addAttribute("schedule", schedule);
		m.addAttribute("movie", movie);
		m.addAttribute("hall", hall);
		m.addAttribute("cinemaName", cinemaName);
		m.addAttribute("index", 3);
		return "pages/SelectSeats";
	}
	
	
	/**
	 * 下单
	 */
	
	@RequestMapping("pay")
	public String pay(Model m,int scheduleId,int userId,int movieId,int sumPrice,String[] slist) {
		
		Movie movie = movieService.getMovie(movieId);
		Schedule schedule = scheduleService.getScheduleByid(scheduleId);
		
		Timestamp startTime = schedule.getStartTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String format = sdf.format(startTime);
		schedule.setShowStartTime(format);
		
		
		Cinema cinema = cinemaService.getCinemaByHallId(schedule.getHallId());
		Hall hall = cinemaService.getHallDetail(schedule.getHallId());	
		
		
		String orderSeat = hall.getName()+" ";
		
		
		//电影票的座位
		List<Ticket> ts = new ArrayList<>();
		for (String seat : slist) {
			Ticket ticket = new Ticket();
			ticket.setSeat(seat);
			ticket.setUserId(userId);
			ticket.setScheduleId(scheduleId);
			ts.add(ticket);
			
			String temp = new String(seat);
			temp = temp.replace(",","排");
			temp+="座 ";
			orderSeat+=temp;
		}
		
		
		//电影名
		m.addAttribute("movieName", movie.getName());
		
		//时间
		m.addAttribute("startTime", schedule.getShowStartTime());
		
		
		//影院
		m.addAttribute("cinemaName", cinema.getName());
		
		//座位
		m.addAttribute("orderSeat", orderSeat);
		
		//总价
		m.addAttribute("sumPrice", sumPrice);
		
		return "pages/OrderConfirm";
	}
	
	
	/**
	 * 确认订单
	 */
	
	@RequestMapping("confirm")
	public String confirm(Model m) {
		return "pages/OrderConfirm";
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
