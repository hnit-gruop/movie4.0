package com.yc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yc.bean.Cinema;
import com.yc.bean.CinemaExample;
import com.yc.bean.Hall;
import com.yc.bean.HallExample;
import com.yc.bean.Movie;
import com.yc.bean.Schedule;
import com.yc.dao.CinemaMapper;
import com.yc.dao.HallMapper;
import com.yc.dao.MovieMapper;
import com.yc.dao.ScheduleMapper;
import com.yc.service.CinemaService;

@Service
public class CinemaServiceImpl implements CinemaService{
	@Resource
	CinemaMapper cinemaMapper;
	@Resource
	HallMapper hallMapper;
	@Autowired
	MovieMapper movieMapper;
	@Autowired
	ScheduleMapper scheduleMapper;
	
	@Override
	public int addCinema(Cinema cinema) {
		int result = cinemaMapper.insertSelective(cinema);
		return result;
	}

	public PageInfo<Cinema> getAllCinema(int pageNum,String name) {
		PageHelper.startPage(pageNum, 5);
		List<Cinema> selectByExample;
		if(name == null || name.trim().length() == 0) {
			selectByExample = cinemaMapper.selectByExample(null);
		}else {
			CinemaExample ce = new CinemaExample();
			PageHelper.startPage(pageNum, 5);
			ce.createCriteria().andNameLike("%"+name+"%");
			selectByExample = cinemaMapper.selectByExample(ce);
		}
		
		PageInfo<Cinema> page = new PageInfo<>(selectByExample);
		return page;
	}

	@Override
	public Cinema getCinemaDetail(int id) {
		Cinema cinema = cinemaMapper.selectByPrimaryKey(id);
		return cinema;
	}

	@Override
	public int updataCinema(Cinema cinema) {
		int result = cinemaMapper.updateByPrimaryKeySelective(cinema);
		return result;
	}

	@Override
	public PageInfo<Cinema> getCinemaByName(String name,int pageNum) {
		CinemaExample ce = new CinemaExample();
		PageHelper.startPage(pageNum, 5);
		ce.createCriteria().andNameLike("%"+name+"%");
		List<Cinema> selectByExample = cinemaMapper.selectByExample(ce);
		PageInfo<Cinema> page = new PageInfo<>(selectByExample);
		return page;
	}
	
	@Override
	public Cinema cinema(int cinemaId) {
		Cinema selectByPrimaryKey = cinemaMapper.selectByPrimaryKey(cinemaId);
		return selectByPrimaryKey;
	}

	@Override
	public List<Movie> getCinemaMovieList() {
		return null;
	}

	@Override
	public List<Cinema> listCinema() {
		List<Cinema> list = cinemaMapper.selectByExample(null);
		return list;
	}

	@Override
	public List<Cinema> getAllCinema(int pageNum) {
		return null;
	}


	@Override
	public int addHall(Hall hall) {
		int result = hallMapper.insertSelective(hall);
		return result;
	}

	

	@Override
	public Hall getHallDetail(int hallId) {
		Hall hall = hallMapper.selectByPrimaryKey(hallId);
		return hall;
	}
	
	public int updataHall(Hall hall) {
		int result = hallMapper.updateByPrimaryKeySelective(hall);
		return result;
	}

	@Override
	public List<Cinema> getAllCinema() {
		List<Cinema> result = cinemaMapper.selectByExample(null);
		return result;
	}

	@Override
	public List<Hall> getHallByCinemaId(int cid) {
		HallExample he = new HallExample();
		he.createCriteria().andCinemaIdEqualTo(cid);
		List<Hall> result = hallMapper.selectByExample(he);
		return result;
	}
	

	@Override
	public List<Movie> getMoiveList(Integer cinemaId) {
		List<Movie> list = movieMapper.listMovie(cinemaId);
		return list;
	}

	@Override
	public List<Schedule> getSchedual(int cinemaId, int movieId) {
		return  scheduleMapper.getSchedual(cinemaId, movieId);
	}

	@Override
	public Cinema getCinemaByHallId(Integer hallId) {
		Hall hall = hallMapper.selectByPrimaryKey(hallId);
		return cinemaMapper.selectByPrimaryKey(hall.getCinemaId());
	}
}
