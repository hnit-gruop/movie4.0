package com.yc.dao;

import com.yc.bean.Movie;
import com.yc.bean.Schedule;
import com.yc.bean.ScheduleExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ScheduleMapper {
    long countByExample(ScheduleExample example);

    int deleteByExample(ScheduleExample example);

    int deleteByPrimaryKey(Integer scheduleId);

    int insert(Schedule record);

    int insertSelective(Schedule record);

    List<Schedule> selectByExample(ScheduleExample example);

    Schedule selectByPrimaryKey(Integer scheduleId);

    int updateByExampleSelective(@Param("record") Schedule record, @Param("example") ScheduleExample example);

    int updateByExample(@Param("record") Schedule record, @Param("example") ScheduleExample example);

    int updateByPrimaryKeySelective(Schedule record);

    int updateByPrimaryKey(Schedule record);

	List<Schedule> getSchedual(int cinemaId, int movieId);
	
	List<Map<String,Object>> getScheduleNum();
	
	List<Map<String,Object>> getTotalTicketOfWeek();
	
	List<Map<String,Object>> getMainMovieTicket();
	
	List<Map<String,Object>> getAllTicter();
}