package com.yc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.bean.Schedule;
import com.yc.bean.ScheduleExample;
import com.yc.dao.ScheduleMapper;
import com.yc.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	@Resource
	ScheduleMapper sm;

	@Override
	public List<Schedule> getScheduleByHallId(int hallId) {
		ScheduleExample se = new ScheduleExample();
		se.createCriteria().andHallIdEqualTo(hallId);
		List<Schedule> result = sm.selectByExample(se);
		return result;
	}

	@Override
	public int addSchedule(Schedule schedule) {
		int result = sm.insertSelective(schedule);
		return result;
	}

}
