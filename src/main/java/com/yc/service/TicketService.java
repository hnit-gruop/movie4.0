package com.yc.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yc.bean.Ticket;

public interface TicketService {
	
	List<Ticket> getSoldByScheduleId(int scheduleId);
	
	PageInfo<Map<String,Object>> getAllTicket(int pageNum);
}
