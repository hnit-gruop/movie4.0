package com.yc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yc.bean.Cinema;
import com.yc.bean.CinemaExample;
import com.yc.bean.Movie;
import com.yc.dao.CinemaMapper;
import com.yc.service.CinemaService;

@Service
public class CinemaServiceImpl implements CinemaService{
	@Resource
	CinemaMapper cinemaMapper;
	
	@Override
	public int addCinema(Cinema cinema) {
		int result = cinemaMapper.insertSelective(cinema);
		return result;
	}

	@Override
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
			PageInfo<Cinema> page = new PageInfo<>(selectByExample);
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

}
