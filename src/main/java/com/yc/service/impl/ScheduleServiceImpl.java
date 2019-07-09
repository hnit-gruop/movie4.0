package com.yc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
	
	@Override
	public List<Map<String, Object>> getScheduleNum() {
		List<Map<String, Object>> scheduleNum = sm.getScheduleNum();
		return scheduleNum;
	}

	@Override
	public int getAllScheduleNum() {
		return sm.selectByExample(null).size();
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> getTotalTicketOfWeek() {
		List<Map<String, Object>> l1 = sm.getTotalTicketOfWeek();
		Map<String,Object> map = new HashMap<>();
		for(int i = 1; i < 7; i++) {
			double total = 0;
			for(Map<String,Object> m:l1) {
				Timestamp time = new Timestamp(System.currentTimeMillis()-(i-1)*24*60*60*1000);
				int hour = time.getHours();
				int min = time.getMinutes();
				int sec = time.getSeconds();
				Long timeNum = time.getTime();
				timeNum = timeNum - (hour*60*60*1000+min*60*1000+sec*1000);
				Timestamp dayTime = new Timestamp(timeNum);
				Timestamp temp = (Timestamp)m.get("start_time");
				if(temp.before(dayTime) && temp.after(new Timestamp(dayTime.getTime()-24*60*60*1000))) {
					total += (double)m.get("price");
					map.put(i+"", total);
				}
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> getMainMovieTicket() {
		List<Map<String, Object>> mainMovieTicket = sm.getMainMovieTicket();
		double total = 0;
		double num = 0;
	
		int count = 0;
		if(mainMovieTicket.size() > 5) {
			count = 5;
		}else {
			count = mainMovieTicket.size();
		}
		String[] nameList = new String[count];
		double[] valueList = new double[count];
		Map<String,Object> map = new HashMap<>();
		int index = 0;
		for(Map<String,Object> m:mainMovieTicket) {
			double price = Double.parseDouble(m.get("value").toString());
			if(index < 5) {
				nameList[index] = m.get("name").toString();
				valueList[index] = price;
				num+=price;
			}
			index++;
			total += price; 
		}
		if(mainMovieTicket.size() > 5) {
			nameList[4] = "其他";
			valueList[4] = total - num;
		}
		List<Map<String,Object>> list = new ArrayList<>();
		for(int i = 0; i < count; i++) {
			Map<String,Object> temp = new LinkedHashMap<>();
			temp.put("value",valueList[i]);
			temp.put("name",nameList[i]);
			
			list.add(temp);
		}
		map.put("nameList",nameList);
		map.put("valueList",list);
		return map;
	}
}
	
