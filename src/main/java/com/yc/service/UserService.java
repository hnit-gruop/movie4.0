package com.yc.service;

import com.yc.bean.User;

public interface UserService {
	User login(String username,String password);
	int reg(User u);
	
	User get(int userId);
	User loginByEamil(String username, String email);
	Boolean isReg(String mobile);
	void addUser(String mobile, String password);
	User login(String phonenum);
}
