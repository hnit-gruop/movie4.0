package com.yc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.bean.MovieOrder;
import com.yc.bean.Ticket;
import com.yc.dao.MovieOrderMapper;
import com.yc.dao.TicketMapper;
import com.yc.service.OrderService;
import com.yc.service.TicketService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	MovieOrderMapper orderMapper;
	
	@Autowired
	TicketMapper ticketMapper;
	
	@Override
	public int insert(MovieOrder order) {
		return orderMapper.insert(order);
	}

	@Override
	public int insertTicket(List<Ticket> ts) {
		for (Ticket ticket : ts) {
			insertTicket(ticket);
		}
		return 1;
	}

	@Override
	public int insertTicket(Ticket ticket) {
		return ticketMapper.insert(ticket);
	}
}
