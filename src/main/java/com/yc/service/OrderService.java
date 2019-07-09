package com.yc.service;

import java.util.List;

import com.yc.bean.MovieOrder;
import com.yc.bean.Ticket;

public interface OrderService {
	int insert(MovieOrder order);

	int insertTicket(List<Ticket> ts);
	
	int insertTicket(Ticket ticket);
}
