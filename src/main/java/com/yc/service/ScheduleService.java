package com.yc.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yc.bean.Schedule;

public interface ScheduleService {
	PageInfo<Schedule> getScheduleByHallId(int hallId,int pageNum);
	
	List<Schedule> getScheduleByHallId(int hallId);
	
	int addSchedule(Schedule schedule);
	
	int updataSchedule(Schedule schedule);
	
	int canBeAdd(Schedule schedule) throws IllegalArgumentException, IllegalAccessException;
	
	Schedule getScheduleByid(int id);
	
	List<Map<String,Object>> getScheduleNum();
	
	int getAllScheduleNum();
	
	Map<String, Object> getTotalTicketOfWeek();
}	
