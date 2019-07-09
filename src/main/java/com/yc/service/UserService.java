package com.yc.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.yc.bean.MovieOrder;
import com.yc.bean.User;

public interface UserService {
	User login(String username,String password);
	int reg(User u);
	
	User get(int userId);
	User loginByEamil(String username, String email);
	Boolean isReg(String mobile);
	void addUser(String mobile, String password, String username, String email);
	User login(String phonenum);
	String isReg2(String a);
	void updatepwd(String email, String password);
	Boolean isNameExist(String username);
	Boolean isEmailExist(String email);
	PageInfo<User> getAllUser(int pageNum, String name);
	
	
	void updateImg(String fileName, String userId);
	Boolean isPhoneExist(String phone);
	void updatedetail(String username, String phone, String email, String userId);
	
	
	List<MovieOrder> getOrders(int userId);
	void setOrderImg(List<MovieOrder> os);
	void setOrderImg(MovieOrder os);
	int updata(User user);
}