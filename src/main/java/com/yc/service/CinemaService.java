package com.yc.service;

import java.util.List;
import com.github.pagehelper.PageInfo;
import com.yc.bean.Cinema;
import com.yc.bean.Hall;
import com.yc.bean.Movie;
import com.yc.bean.Schedule;
import com.yc.service.CinemaService;

public interface CinemaService {
	Cinema cinema(int cinemaId);
	
	List<Movie> getCinemaMovieList();

	List<Cinema> listCinema();
	
	int addCinema(Cinema cinema);
	
	List<Cinema> getAllCinema(int pageNum);
	
	Cinema getCinemaDetail(int id);
	
	int updataCinema(Cinema cinema);
	
	PageInfo<Cinema> getCinemaByName(String name,int pageNum);
	
	PageInfo<Cinema> getAllCinema(int pageNum,String name);
	
	int addHall(Hall hall);
	
	List<Hall> getHallByCinemaId(int cid);
	
	List<Cinema> getAllCinema();
	
	Hall getHallDetail(int hallId);
	
	int updataHall(Hall hall);

	List<Movie> getMoiveList(Integer cinemaId);

	List<Schedule> getSchedual(int cinemaId, int movieId);
	
	
}
