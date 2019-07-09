package com.yc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yc.service.impl.ScheduleServiceImpl;
import com.yc.service.impl.TicketServiceImpl;

@Controller
public class DataStaticController {
	@Resource
	ScheduleServiceImpl ssi;
	@Resource
	TicketServiceImpl tsi;
	
	@RequestMapping("getData")
	public ModelAndView getData(ModelAndView model){
		model.setViewName("manage/dateStatistics");
		return model;
	}
	
	@RequestMapping("getScheduleNumberData")
	@ResponseBody
	public Map<String,Object> getScheduleNumberData(){
		List<Map<String, Object>> scheduleNum = ssi.getScheduleNum();
		String[] nameList;
		int[] valueList;
		if(scheduleNum.size() <= 7) {
			nameList = new String[scheduleNum.size()];
			valueList = new int[scheduleNum.size()];
		}else {
			nameList = new String[7];
			valueList = new int[7];
		}
		
		int index = 0;
		
		if(scheduleNum.size() > 7) {
			int total = ssi.getAllScheduleNum();
			int count = 0;
			for(Map<String,Object> map:scheduleNum) {
				nameList[index] = map.get("name").toString();
				valueList[index] = Integer.parseInt(map.get("value").toString());
				count += Integer.parseInt(map.get("value").toString());
				index++;
				if(index >= 6) {
					break;
				}
			}
			nameList[6] = "其他";
			valueList[6] = total-count;
		}else {
			
		}
		Map<String,Object> result = new HashMap<>();
		result.put("nameList", nameList);
		result.put("valueList", valueList);
		return result;
	}
	
	@RequestMapping("getTotalTicketOfWeek")
	@ResponseBody
	public Map<String,Object> getTotalTicketOfWeek() {
		Map<String, Object> totalTicketOfWeek = ssi.getTotalTicketOfWeek();
		Date data = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] nameList = new String[7];
		double[] valueList = {0,0,0,0,0,0,0};
		for(int i = 1; i <= 7; i++) {
			 Date temp = new Date(data.getTime()-i*24*60*60*1000);
			 nameList[i-1] = sdf.format(temp);
			 if(totalTicketOfWeek.get(i+"") != null) {
				 valueList[i-1] = Double.parseDouble(totalTicketOfWeek.get(i+"").toString());
			 }
		}
		Map<String, Object> map = new HashMap<>();
		map.put("nameList", nameList);
		map.put("valueList", valueList);
		return map;
	}
	
	@RequestMapping("mainMovieTicket")
	@ResponseBody
	public Map<String,Object> mainMovieTicket(){
		return ssi.getMainMovieTicket();
	}
	
	@RequestMapping("getAllTicket")
	public ModelAndView getAllTicket(ModelAndView model){
		PageInfo<Map<String, Object>> allTicket = tsi.getAllTicket(1);
		int total = (int) allTicket.getTotal();
		if(total % 5 == 0) {
			total = total / 5;
		}else {
			total = total / 5 + 1;
		}
		List<Map<String,Object>> list = new ArrayList<>();
		list = allTicket.getList();
		model.addObject("total",total);
		model.addObject("ticketList",list);
		model.setViewName("manage/allticket");
		return model;
	}
	
	@RequestMapping("getAllTicketByPage")
	@ResponseBody
	public List<Map<String,Object>> getAllTicket(@RequestParam(name="pageNum",defaultValue="1") int pageNum){
		PageInfo<Map<String, Object>> allTicket = tsi.getAllTicket(pageNum);
		int total = (int) allTicket.getTotal();
		if(total % 5 == 0) {
			total = total / 5;
		}else {
			total = total / 5 + 1;
		}
		List<Map<String,Object>> list = allTicket.getList();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total",total);
		list.add(map);
		return list;
	}
}
