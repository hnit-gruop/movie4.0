package com.yc.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yc.bean.User;
import com.yc.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("logout")
	public String  logout(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		User u = (User) request.getSession().getAttribute("user");
		if(u!=null) {
			request.getSession().removeAttribute("user");
		}
		Cookie[] cookies=request.getCookies();
		for(Cookie cookie:cookies){
			if("uname".equals(cookie.getName())){
				cookie.setMaxAge(0);			
				response.addCookie(cookie);
			}
		}
		return "redirect:index";
	}
	

	@RequestMapping("/doLogin")
	public String login(Model m,String phonenum,String password
			,HttpSession session,String type,HttpServletResponse response
			,String code,String code2) {
		User user=null;
		System.out.println(phonenum);
		m.addAttribute("type",type);
		m.addAttribute("phonenum",phonenum);
		m.addAttribute("password",password);
		if(type.equals("1")){
			if(phonenum.trim().equals("") || password.trim().equals("")){
				m.addAttribute("msg","手机号或密码不能为空");
				return "pages/Login1";
			}else{
				 user = userService.login(phonenum, password);
					if(user==null) {
						m.addAttribute("msg","手机号或密码错误");
						return "pages/Login1";
					}else{
						Cookie cookie=new Cookie("uname",user.getUsername());
						cookie.setMaxAge(3*60);
						cookie.setPath("/");
						response.addCookie(cookie);
						session.setAttribute("user", user);
						return "redirect:index";
					}					
			}			
		}	
		if(type.equals("2")){
			if(phonenum.trim().equals("") || code.trim().equals("")){
				m.addAttribute("msg","手机号或动态码不能为空");
				return "pages/Login1";
			}else{
				 user = userService.login(phonenum);
				if(user==null){
					m.addAttribute("msg","该手机号未注册");
					return "pages/Login1";
				}else{
					if(code.trim().equals(code2.trim())){
						Cookie cookie=new Cookie("uname",user.getUsername());
						cookie.setMaxAge(3*60);
						cookie.setPath("/");
						response.addCookie(cookie);
						session.setAttribute("user", user);
						return "redirect:index";
					}else{
						m.addAttribute("msg","动态码错误");
						return "pages/Login1";
					}
				}
				
				
			}
		}
		return "redirect:login";
	}
	
    @RequestMapping("/login")
    public String log(Model m,String type) {
    	m.addAttribute("type",type);
        return "pages/Login1";
    }
	
    @RequestMapping("/reg")
    public String reg(Model m,String msg) {
    	m.addAttribute("msg",msg);
        return "pages/Register1";
    }
    
    @RequestMapping("/doReg")
    public void doReg( Model m, String mobile,String password,HttpServletResponse response ,HttpSession session) {
    	User user = null;
    	Boolean user1;
    	m.addAttribute("phone",mobile);
    	m.addAttribute("password",password);
    	user1= userService.isReg(mobile);
    	if(user1==true){
    		try {
				response.getWriter().write("no");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}else{
    		userService.addUser(mobile,password);
    		user = userService.login(mobile, password);
    		Cookie cookie=new Cookie("uname",user.getUsername());
			cookie.setMaxAge(3*60);
			cookie.setPath("/");
			response.addCookie(cookie);
			session.setAttribute("user", user);
    		try {
				response.getWriter().write("yes");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    		
    	
    	
    }
	
    
    @RequestMapping("/getCode")
    public String getCode(Model m,String type,String phonenum) {
    	m.addAttribute("code",123456);
    	m.addAttribute("phonenum",phonenum);
    	m.addAttribute("type",type);
        return "pages/Login1";
    }
    
	@RequestMapping("t")
	public String test1(){
		return "pages/test";
	}
}

