package com.yc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yc.bean.Movie;
import com.yc.bean.Schedule;
import com.yc.bean.ScheduleExample;
import com.yc.dao.ScheduleMapper;
import com.yc.service.ScheduleService;
import com.yc.util.Utils;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	@Resource
	ScheduleMapper sm;
	@Resource
	MovieServiceImpl msi;

	@Override
	public PageInfo<Schedule> getScheduleByHallId(int hallId,int pageNum) {
		PageHelper.startPage(pageNum, 5);
		ScheduleExample se = new ScheduleExample();
		se.createCriteria().andHallIdEqualTo(hallId);
		List<Schedule> result = sm.selectByExample(se);
		PageInfo<Schedule> page = new PageInfo<>(result);
		return page;
	}

	@Override
	public int addSchedule(Schedule schedule) {
		int result = sm.insertSelective(schedule);
		return result;
	}

	@Override
	public int canBeAdd(Schedule schedule) throws IllegalArgumentException, IllegalAccessException {
		List<Schedule> list_1 = getScheduleByHallId(schedule.getHallId());
		List<Map<String,Object>> list = new ArrayList<>(); 
		for(Schedule s:list_1) {
			Map<String,Object> map = new HashMap<>();
 			Utils.transformBeanToMap(s, map);
 			Movie movie = msi.get(s.getMovieId());
 			long duration = movie.getDuration()*60*1000;
 			String name = movie.getName();
 			Timestamp startTime = s.getStartTime();
 			long start = startTime.getTime();
 			long end = start + duration;
 			Timestamp endTime = new Timestamp(end);
 			map.put("endTime",endTime);
 			map.put("name",name);
 			list.add(map);
		}
		
		Timestamp begin_time = schedule.getStartTime();
		long _start = begin_time.getTime();
		Movie _m = msi.get(schedule.getMovieId());
		long _duration = _m.getDuration()*60*1000;
		long _end = _start + _duration;
		Timestamp end_Time = new Timestamp(_end);
		
		for(Map<String,Object> map:list) {
			if(((Timestamp)map.get("startTime")).before(begin_time) && begin_time.before((Timestamp)map.get("endTime"))) {
				return -1;
			}
			if(((Timestamp)map.get("startTime")).before(end_Time) && end_Time.before((Timestamp)map.get("endTime"))) {
				return -1;
			}
			if(begin_time.before(((Timestamp)map.get("startTime"))) && ((Timestamp)map.get("startTime")).before(end_Time)) {
				return -1;
			}
			if(begin_time.before(((Timestamp)map.get("endTime"))) && ((Timestamp)map.get("endTime")).before(end_Time)) {
				return -1;
			}
			if(begin_time.before(((Timestamp)map.get("startTime"))) && ((Timestamp)map.get("endTime")).before(end_Time)) {
				return -1;
			}
			if(begin_time.after(((Timestamp)map.get("startTime"))) && ((Timestamp)map.get("endTime")).after(end_Time)) {
				return -1;
			}
		}
		return 1;
	}

	@Override
	public List<Schedule> getScheduleByHallId(int hallId) {
		ScheduleExample se = new ScheduleExample();
		se.createCriteria().andHallIdEqualTo(hallId);
		List<Schedule> result = sm.selectByExample(se);
		return result;
	}

	@Override
	public Schedule getScheduleByid(int id) {
		Schedule s = sm.selectByPrimaryKey(id);
		return s;
	}

	@Override
	public int updataSchedule(Schedule schedule) {
		int result = sm.updateByPrimaryKeySelective(schedule);
		return result;
	}


}
