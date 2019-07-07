package com.yc.bean;

import java.sql.Timestamp;

public class Schedule {
    private Integer scheduleId;

    private Integer movieId;

    private Integer hallId;

    private Double price;

    private Timestamp startTime;

    private Integer remain;
    
    //关联字段
    private Hall hall;
    
    private Timestamp endTime;
    
    private String showStartTime;
    
    private String showEndTime;
    
    private String monthDay;
    
    

	public String getMonthDay() {
		return monthDay;
	}

	public void setMonthDay(String monthDay) {
		this.monthDay = monthDay;
	}

	public String getShowStartTime() {
		return showStartTime;
	}

	public void setShowStartTime(String showStartTime) {
		this.showStartTime = showStartTime;
	}

	public String getShowEndTime() {
		return showEndTime;
	}

	public void setShowEndTime(String showEndTime) {
		this.showEndTime = showEndTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

	@Override
	public String toString() {
		return "Schedule [scheduleId=" + scheduleId + ", movieId=" + movieId + ", hallId=" + hallId + ", price=" + price
				+ ", startTime=" + startTime + ", remain=" + remain + ", hall=" + hall + "]";
	}
    
}