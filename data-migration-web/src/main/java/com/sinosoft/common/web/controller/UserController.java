package com.sinosoft.common.web.controller;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.SimpleTimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private IUserService userService;

	/** 服务器启动时间 */
	private static final Date ServerStartTime = new Date();
	
	@RequestMapping(value = "/index.do")
	public String index() {

		return "/admin/login";
	}

	@RequestMapping(value = "/login.do")
	public String login(@RequestParam(value = "usercode") String usercode,
						@RequestParam(value = "password") String password,
						HttpServletRequest request){

		try {

			HttpSession session = request.getSession();
			session.setAttribute("user",userService.login(usercode,password));
		} catch (NonePrintException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg",e.getErrMsg());
			return "/admin/login";
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "/admin/login";
		}
		return "/admin/index";
	}

	@RequestMapping(value = "/logout.do")
	public String logout(HttpSession session){

		session.removeAttribute("user");

		return "/admin/login";
	}

	@RequestMapping(value = "/welcome.do")
	public ModelAndView welcome(HttpServletRequest request) {


		ModelAndView mv = new ModelAndView();


		mv.setViewName("admin/welcome");
		return mv;
	}

}
