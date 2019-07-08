package com.yc.service;

import java.util.List;

import com.yc.bean.Ticket;

public interface TicketService {
	
	List<Ticket> getSoldByScheduleId(int scheduleId);
}
