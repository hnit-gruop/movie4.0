package com.yc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.bean.Ticket;
import com.yc.bean.TicketExample;
import com.yc.dao.TicketMapper;
import com.yc.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService{
	
	@Autowired
	TicketMapper ticketMapper;
	
	@Override
	public List<Ticket> getSoldByScheduleId(int scheduleId) {
		
		TicketExample e = new TicketExample();
		e.createCriteria().andScheduleIdEqualTo(scheduleId);
		List<Ticket> list = ticketMapper.selectByExample(e);
		
		return list;
	}
	
}
