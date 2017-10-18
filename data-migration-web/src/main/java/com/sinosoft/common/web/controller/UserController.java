package com.sinosoft.common.web.controller;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.datamigration.common.Pager;
import com.sinosoft.datamigration.exception.NonePrintException;
import com.sinosoft.datamigration.po.Dmuserinfo;
import com.sinosoft.datamigration.service.IUserService;
import com.sinosoft.datamigration.util.AssertUtils;
import com.sinosoft.datamigration.util.ErrorCodeDesc;
import com.sinosoft.datamigration.util.ObjectUtils;
import com.sinosoft.datamigration.util.ResultDesc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private IUserService userService;

	/** 服务器启动时间 */
	private static final Date ServerStartTime = new Date();
	
	@RequestMapping("/index")
	public String index() {

		return "/admin/index";
	}

	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> login(@RequestParam(value = "usercode") String usercode,
						@RequestParam(value = "password") String password,
						HttpServletRequest request){

		Map<String,Object> resultMap = new HashMap<>();

		try {

			HttpSession session = request.getSession();
			session.setAttribute("user",userService.login(usercode,password));

			resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);

		} catch (NonePrintException e) {
			e.printStackTrace();
			resultMap.put(ResultDesc.CODE,e.getErrCode());
			resultMap.put(ResultDesc.MSG,e.getErrMsg());
		}
		return resultMap;
	}

	@RequestMapping(value = "/logout.do")
	public String logout(HttpSession session){

		session.removeAttribute("user");

		return "/admin/login";
	}

	@RequestMapping("/addUserInfo")
	@ResponseBody
	public Map<String, Object> addUserInfo(Dmuserinfo dmuserinfo,
										   @RequestParam(value = "passwordBak",required = false) String passwordBak){

		Map<String,Object> resultMap = new HashMap<>();

		try {
			if(AssertUtils.isEmpty(dmuserinfo.getUsercode()) 		||
					AssertUtils.isEmpty(dmuserinfo.getUsername()) 	||
					AssertUtils.isEmpty(dmuserinfo.getPassword()) 	||
					AssertUtils.isEmpty(passwordBak) 				||
					!passwordBak.equals(dmuserinfo.getPassword())) {

				throw new NonePrintException(ErrorCodeDesc.SYSTEM_ERROR.getCode(),ErrorCodeDesc.SYSTEM_ERROR.getDesc());
			}

			dmuserinfo.setAddress("中科软系统");
			dmuserinfo.setCreatetime(new Date());
			userService.addUser(dmuserinfo);

			resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
			resultMap.put(ResultDesc.MSG,"保存成功");
		} catch (NonePrintException e) {
			e.printStackTrace();
			resultMap.put(ResultDesc.CODE,e.getErrCode());
			resultMap.put(ResultDesc.MSG,e.getErrMsg());
		}
		return resultMap;
	}

	@RequestMapping("/updateUserInfo")
	@ResponseBody
	public Map<String, Object> updateUserInfo(Dmuserinfo dmuserinfo,
										   @RequestParam(value = "passwordBak",required = false) String passwordBak){

		Map<String,Object> resultMap = new HashMap<>();

		try {
			if(AssertUtils.isEmpty(dmuserinfo.getUsercode()) 		||
					AssertUtils.isEmpty(dmuserinfo.getUsername()) 	||
					AssertUtils.isEmpty(dmuserinfo.getPassword()) 	||
					AssertUtils.isEmpty(passwordBak) 				||
					!passwordBak.equals(dmuserinfo.getPassword())) {

				throw new NonePrintException(ErrorCodeDesc.SYSTEM_ERROR.getCode(),ErrorCodeDesc.SYSTEM_ERROR.getDesc());
			}

			dmuserinfo.setUpdatetime(new Date());
			userService.updateUserInfo(dmuserinfo);

			resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
			resultMap.put(ResultDesc.MSG,"修改成功");
		} catch (NonePrintException e) {
			e.printStackTrace();
			resultMap.put(ResultDesc.CODE,e.getErrCode());
			resultMap.put(ResultDesc.MSG,e.getErrMsg());
		}
		return resultMap;
	}

	@RequestMapping("/updatePassword")
	@ResponseBody
	public Map<String, Object> updatePassword(@RequestParam(value = "passwordOld",required = false) String passwordOld,
											  @RequestParam(value = "passwordNew",required = false) String passwordNew,
											  @RequestParam(value = "passwordNewBak",required = false) String passwordNewBak,
											  HttpSession session){

		Map<String,Object> resultMap = new HashMap<>();

		try {

			Dmuserinfo userInfo = (Dmuserinfo) session.getAttribute("user");

			if(userInfo == null){
				throw new NonePrintException(ErrorCodeDesc.SESSION_USER_OBSOLETE.getCode(),ErrorCodeDesc.SESSION_USER_OBSOLETE.getDesc());
			}

			if(AssertUtils.isEmpty(passwordOld) 		||
					AssertUtils.isEmpty(passwordNew) 	||
					AssertUtils.isEmpty(passwordNewBak)) {

				throw new NonePrintException(ErrorCodeDesc.SYSTEM_ERROR.getCode(),ErrorCodeDesc.SYSTEM_ERROR.getDesc());
			}

			if(!passwordNew.equals(passwordNewBak)){
				throw new NonePrintException(ErrorCodeDesc.SYSTEM_ERROR.getCode(),ErrorCodeDesc.SYSTEM_ERROR.getDesc());
			}

			userService.updatePassword(userInfo,passwordOld,passwordNew);

			resultMap.put(ResultDesc.CODE,ResultDesc.SUCCESS);
			resultMap.put(ResultDesc.MSG,"修改成功");
		} catch (NonePrintException e) {
			e.printStackTrace();
			resultMap.put(ResultDesc.CODE,e.getErrCode());
			resultMap.put(ResultDesc.MSG,e.getErrMsg());
		}
		return resultMap;
	}

	@RequestMapping(value = "/editShowUserInfo")
	public String editShowUserInfo(String usercode, HttpServletRequest request){

		try {
			request.setAttribute("dmuserinfo",userService.findUserInfoByCode(usercode));
		} catch (NonePrintException e) {
			e.printStackTrace();
		}
		return "/user/user_edit";
	}

	@RequestMapping("/queryUserInfo")
	@ResponseBody
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
