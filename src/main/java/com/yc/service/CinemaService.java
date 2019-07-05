package com.yc.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.yc.bean.Cinema;

public interface CinemaService {
	int addCinema(Cinema cinema);
	PageInfo<Cinema> getAllCinema(int pageNum,String name);
	
	Cinema getCinemaDetail(int id);
	
	int updataCinema(Cinema cinema);
	
	PageInfo<Cinema> getCinemaByName(String name,int pageNum);
}
