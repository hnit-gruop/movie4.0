package com.yc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yc.bean.Ticket;
import com.yc.bean.TicketExample;
import com.yc.bean.User;
import com.yc.bean.UserExample;
import com.yc.dao.ScheduleMapper;
import com.yc.dao.TicketMapper;
import com.yc.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService{
	
	@Autowired
	TicketMapper ticketMapper;
	@Resource
	ScheduleMapper scheduleMapper;
	
	@Override
	public List<Ticket> getSoldByScheduleId(int scheduleId) {
		
		TicketExample e = new TicketExample();
		e.createCriteria().andScheduleIdEqualTo(scheduleId);
		List<Ticket> list = ticketMapper.selectByExample(e);
		
		return list;
	}

	@Override
	public PageInfo<Map<String, Object>> getAllTicket(int pageNum) {
		PageHelper.startPage(pageNum, 5);
		List<Map<String, Object>> allTicter = scheduleMapper.getAllTicter();
		PageInfo<Map<String, Object>> page = new PageInfo<>(allTicter);
		return page;
	}
	
}
