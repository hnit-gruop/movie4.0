package com.yc.service;

import java.util.List;

import com.yc.bean.Cinema;
import com.yc.bean.Movie;

public interface CinemaService {
	Cinema cinema(int cinemaId);
	
	List<Movie> getCinemaMovieList();

	List<Cinema> listCinema();
	
	int addCinema(Cinema cinema);
	
	List<Cinema> getAllCinema(int pageNum);
	
	Cinema getCinemaDetail(int id);
	
	int updataCinema(Cinema cinema);
	
	List<Cinema> getCinemaByName(String name,int pageNum);

}
