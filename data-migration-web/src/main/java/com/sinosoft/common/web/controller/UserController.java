package com.sinosoft.common.web.controller;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.service.IUserService;
import com.sinosoft.datamigration.util.ObjectUtils;
import com.sinosoft.datamigration.util.ResultDesc;
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
		}
		return "/admin/index";
	}

	@RequestMapping(value = "/logout.do")
	public String logout(HttpSession session){

		session.removeAttribute("user");

		return "/admin/login";
	}

	public Map<String, Object> queryUserInfo(@RequestParam(value = "username",required = false) String username,
											 @RequestParam(value = "isvalid",required = false) String isvalid,
											 Pager pager){

		Map<String,Object> resultMap = new HashMap<>();

		Map<String, Object> paramMap = new HashMap<>();
		if(!ObjectUtils.isEmpty(username)){
			paramMap.put("username",username);
		}
		if(!ObjectUtils.isEmpty(isvalid)){
			paramMap.put("isvalid",isvalid);
		}

		try {
			pager = userService.queryUserInfoByMap(pager,paramMap);
			resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
			resultMap.put("pager",pager);
		} catch (NonePrintException e) {
			e.printStackTrace();
			resultMap.put(ResultDesc.CODE,e.getErrCode());
			resultMap.put(ResultDesc.MSG,e.getErrMsg());
		}
		return resultMap;
	}

	@RequestMapping(value = "/welcome.do")
	public ModelAndView welcome(HttpServletRequest request) {


		ModelAndView mv = new ModelAndView();


		mv.setViewName("admin/welcome");
		return mv;
	}

}
