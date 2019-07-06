package com.yc.service;

import java.util.List;

import com.yc.bean.Schedule;

public interface ScheduleService {
	List<Schedule> getScheduleByHallId(int hallId);
	
	int addSchedule(Schedule schedule);
}	
