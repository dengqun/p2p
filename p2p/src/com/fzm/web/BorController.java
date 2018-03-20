package com.fzm.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
import com.fzm.daoimpl.ProducetDaoImpl;
import com.fzm.entity.Borrow;
import com.fzm.entity.ProducetVO;
import com.fzm.entity.User;
import com.fzm.service.BorService;
import com.fzm.service.serviceImpl.UserLoginServiceImpl;
import com.fzm.tools.ChangeDate;
import com.fzm.tools.JSONUtil;
import com.fzm.tools.Page;

/**
 * 
 * @Description : 接受用户请求 进行处理数据
 * @author sdy
 * @date 2017-08-01
 * 
 */
@Controller
public class BorController {

	@Autowired
	BorService borService;
//	@Autowired
//	private UserLoginServiceImpl userLoginService;
	@Resource(name="producet")
	private ProducetDaoImpl fser;
	@Resource(name="userLoginService")
	private UserLoginServiceImpl userLoginService;
	private static final Logger logger = Logger.getLogger(BorController.class);  
	/**
	 * 进入借款主页
	 * 
	 * @param uid
	 *            用户Id
	 * @return
	 */

	@RequestMapping(value = "api/BorMain")
	@ResponseBody
	public Object BorMain(HttpSession session,HttpServletResponse resp,HttpServletRequest req) {
		resp.setCharacterEncoding("utf-8");
    	Map<String, Object> map = new HashMap<String, Object>();
    	String token = req.getHeader("Authorization").toString();
		String uid = userLoginService.ValiToken(req);
		if("".equals(uid)){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "登录信息失效");
			return map;
		}
		int nuid=Integer.parseInt(uid);
//		Integer uid=(Integer)session.getAttribute("userid");
	
		User user = null;
		try {
			user = borService.BorMain(nuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "出现错误");
			return map;
		}
		map.put("code", 200);
		map.put("success", true);
		map.put("message", "查询成功");
		map.put("data", user);
		//System.out.println("---------------"+JSON.toJSON(user));
		return map;
	}
	
	/**
	 * 查询借款用户
	 * @param session
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "api/queryBorrowUser")
	@ResponseBody
	public Object queryBorrowUser(HttpSession session,HttpServletResponse resp,HttpServletRequest req) {
		resp.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
//		Integer uid=(Integer)session.getAttribute("userid");
		String token = req.getHeader("Authorization").toString();
		String uid = userLoginService.ValiToken(req);
		if("".equals(uid)){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "登录信息失效");
			return map;
		}
		int nuid=Integer.parseInt(uid);
		if(uid==null){
			map.put("code", 0);
			map.put("success", true);
			map.put("message", "你的登陆信息失效");
		}
		User user = null;
		try {
			user = borService.queryBorrowUser(nuid);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "服务器内部错误");
		}
		map.put("code", 200);
		map.put("success", true);
		map.put("message", "查询成功");
		map.put("data", user);
		return map;
	}
	
	/**
	 * 查询借款列表
	 * @param session
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "api/queryBorrowList")
	@ResponseBody
	public Object queryBorrowList(HttpSession session,@RequestBody String data,HttpServletResponse resp,HttpServletRequest req) {
		resp.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String,Object> mapParam = JSONUtil.jsonToMap(data);
		int currentPage;
		int pageSize;
//		Integer uid=(Integer)session.getAttribute("userid");
		String token = req.getHeader("Authorization").toString();
		String uid = userLoginService.ValiToken(req);
		if("".equals(uid)){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "登录信息失效");
			return map;
		}
		int nuid=Integer.parseInt(uid);
		if(mapParam.get("currentPage")==null||"".equals(mapParam.get("currentPage"))||mapParam.get("pageSize")==null||"".equals(mapParam.get("pageSize"))){
			currentPage=1;
			pageSize=10;
		}else{
			currentPage=Integer.parseInt(mapParam.get("currentPage").toString());
			pageSize=Integer.parseInt(mapParam.get("pageSize").toString());
		}
		
		
//		if(uid==null){
//			map.put("code", 200);
//			map.put("success", true);
//			map.put("message", "你的登陆信息失效");
//		}
		Page<Borrow> page = null;
		try {
			page = borService.queryBorrowList(currentPage,pageSize,nuid);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "服务器内部错误");
		}
		map.put("code", 200);
		map.put("success", true);
		map.put("message", "查询成功");
		map.put("data", page);
		return map;
	}
	
	
	@RequestMapping("api/queryRechargeRecord")
	@ResponseBody
	public Object queryRechargeRecord(@RequestBody String data, Model m,HttpSession session,HttpServletResponse resp,HttpServletRequest req) throws UnsupportedEncodingException {
		resp.setCharacterEncoding("utf-8");
		data=new String(data.getBytes("ISO-8859-1"), "UTF-8");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String,Object> mapParam = JSONUtil.jsonToMap(data);
		int currentPage;
		int pageSize;
		String token = req.getHeader("Authorization").toString();
		String uid = userLoginService.ValiToken(req);
		if("".equals(uid)){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "登录信息失效");
			return map;
		}
		int nuid=Integer.parseInt(uid);
		if(mapParam.get("currentPage")==null||"".equals(mapParam.get("currentPage"))||mapParam.get("pageSize")==null||"".equals(mapParam.get("pageSize"))){
			currentPage=1;
			pageSize=10;
		}else{
			currentPage=Integer.parseInt(mapParam.get("currentPage").toString());
			pageSize=Integer.parseInt(mapParam.get("pageSize").toString());
		}
		
//		Integer uid=(Integer)session.getAttribute("userid");
//		if(uid==null){
//			map.put("code", 0);
//			map.put("success", false);
//			map.put("message", "你已经登陆失效，重新登陆");
//			return map;
//		}
		Page<Borrow> page = null;
		try {
			page = borService.queryRechargeRecord(nuid, currentPage, pageSize);
		} catch (Exception e) {
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "服务器内部错误");
		}
		map.put("code", 200);
		map.put("success", true);
		map.put("message", "查询成功");
		map.put("data", page);
		return map;
	}
	
	
	@RequestMapping("api/queryExtractList")
	@ResponseBody
	public Object queryExtractList(@RequestBody String data, Model m,HttpSession session,HttpServletResponse resp,HttpServletRequest req) throws UnsupportedEncodingException {
		resp.setCharacterEncoding("utf-8");
		data=new String(data.getBytes("ISO-8859-1"), "UTF-8");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String,Object> mapParam = JSONUtil.jsonToMap(data);
		int currentPage;
		int pageSize;
		String token = req.getHeader("Authorization").toString();
		String uid = userLoginService.ValiToken(req);
		if("".equals(uid)){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "登录信息失效");
			return map;
		}
		int nuid=Integer.parseInt(uid);
		if(mapParam.get("currentPage")==null||"".equals(mapParam.get("currentPage"))||mapParam.get("pageSize")==null||"".equals(mapParam.get("pageSize"))){
			currentPage=1;
			pageSize=10;
		}else{
			currentPage=Integer.parseInt(mapParam.get("currentPage").toString());
			pageSize=Integer.parseInt(mapParam.get("pageSize").toString());
		}
		
//		Integer uid=(Integer)session.getAttribute("userid");
//		if(uid==null){
//			map.put("code", 0);
//			map.put("success", false);
//			map.put("message", "你已经登陆失效，重新登陆");
//			return map;
//		}
		Page<Borrow> page = null;
		try {
			page = borService.queryExtractList(nuid, currentPage, pageSize);
		} catch (Exception e) {
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "服务器内部错误");
		}
		map.put("code", 200);
		map.put("success", true);
		map.put("message", "查询成功");
		map.put("data", page);
		return map;
	}
	/**
	 * 同意利率
	 * @param data
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "api/acceptApplyFor")
	@ResponseBody
	public Object acceptApplyFor(@RequestBody String data,HttpServletResponse resp) {
		resp.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String,Object> mapParam = JSONUtil.jsonToMap(data);
		if(mapParam.get("borId")==null||mapParam.get("borId")==null){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "缺少借款id和同意参数");
			return map;
		}
		int borId = Integer.parseInt(mapParam.get("borId").toString());
		int status = Integer.parseInt(mapParam.get("status").toString());// 1同意，0拒绝
		
		Map<String,Object> mma = JSONUtil.jsonToMap(mapParam.get("sign").toString());
		if(mma.get("sid")==null||mma.get("signdata")==null){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "sid或者signdata参数");
			return map;
		}
		String sid = (mma.get("sid").toString());
		String signdata = (mma.get("signdata").toString());
		try {
			map = borService.acceptApplyFor(new Date(), borId,status,sid,signdata);
		} catch (Exception e) {
			map.put("code", 0);
			map.put("success", false);
			map.put("message", e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 提交借款
	 * 
	 * @param uid
	 *            用户Id
	 * @param interest
	 *            借款利率
	 * @param borDate
	 *            借款申请日
	 * @param borMoney
	 *            借款金额
	 * @param borUse
	 *            借款用途
	 * @param instalment
	 *            是否分期
	 * @param mortgageNo
	 *            合同抵押编号
	 * @param borDeadline
	 *            借款期限
	 * @return
	 * @throws UnsupportedEncodingException 
	 */

	@RequestMapping("api/applyFor")
	@ResponseBody
	public Object applyFor(Model m,@RequestBody String data,HttpSession session,HttpServletRequest request,HttpServletResponse resp) throws UnsupportedEncodingException {
		resp.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		data=new String(data.getBytes("ISO-8859-1"), "UTF-8");
		Map<String,Object> mapParam = JSONUtil.jsonToMap(data);
		if(mapParam.get("interest")==null||mapParam.get("borMoney")==null||mapParam.get("instalment")==null||mapParam.get("repaymodel")==null||mapParam.get("borDeadline")==null||mapParam.get("borUse")==null||mapParam.get("mortgageNo")==null){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "缺少必填参数");
			return map;
		}
		if(mapParam.get("sign")==null){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "缺少签名参数");
			return map;
		}
  		Map<String,Object> mma = JSONUtil.jsonToMap(mapParam.get("sign").toString());
		double interest = Double.parseDouble(mapParam.get("interest").toString());
		double borMoney = Double.parseDouble(mapParam.get("borMoney").toString());
		String borUse = mapParam.get("borUse").toString();
		String mortgageNo = mapParam.get("mortgageNo").toString();
		int borDeadline = Integer.parseInt(mapParam.get("borDeadline").toString());
		String instalment = mapParam.get("instalment").toString();
		String repaymodel = mapParam.get("repaymodel").toString();
		
		String uid = userLoginService.ValiToken(request);
		if("".equals(uid)){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "登录信息过期,请重新登录");
			return map;
		}
		int nuid=Integer.parseInt(uid);
		if(mapParam.get("attachmentid")==null||"".equals(mapParam.get("attachmentid"))){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "请上传合同附件");
			return map;
		}
		String attachmentid = mapParam.get("attachmentid").toString();
		String sid = (mma.get("sid").toString());
		String signdata = (mma.get("signdata").toString());
		
			try {
				map = borService.applyFor(nuid, interest, borMoney,
						borUse, instalment, mortgageNo, borDeadline,attachmentid,repaymodel,sid,signdata);
		  		
			} catch (Exception e) {
				map.put("code", 0);
				map.put("success", false);
				map.put("message", e.getMessage());
				logger.warn("apply borrow exception info come from server,dengqun:pppppppppppppppppp"+e.getMessage()); 
				System.out.println(e.getMessage());
				
				return map;
			}
		
//		map.put("code", 200);
		return map;

	}
	
	/**
	 * 附件上传
	 * @param files
	 * @param session
	 * @param request
	 * @param resp
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("api/uploadFile")
	@ResponseBody
	public Object uploadFile(@RequestParam MultipartFile[] file,HttpSession session,HttpServletRequest request,HttpServletResponse resp) throws UnsupportedEncodingException {
		resp.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		
		String uid = userLoginService.ValiToken(request);
		if("".equals(uid)){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "登录信息失效");
			return map;
		}
//		int nuid=Integer.parseInt(uid);
		String result = null;
			 if(file!=null&&file.length>0){  
			 } else{
				    map.put("code", 0);
					map.put("success", false);
					map.put("message", "申请需要上传合同附件");
					return map; 
			 }

			try {
				result = borService.uploadFile(file,request,uid);
			} catch (Exception e) {
				map.put("code", 0);
				map.put("success", false);
				map.put("message", "上传时出现服务器内部错误");
				System.out.println(e.getMessage());
			}
		
		map.put("code", 200);
		map.put("success", true);
		map.put("message", "附件上传成功");
		map.put("data", result);
		return map;

	}

    
	/**
	 * 还款操作
	 * 
	 * @param uid
	 *            用户Id
	 * @param borId
	 *            借款Id
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("api/rePay")
	@ResponseBody
	public Object rePay(@RequestBody String data, Model m,HttpSession session,HttpServletResponse resp,HttpServletRequest request) throws UnsupportedEncodingException {
		resp.setCharacterEncoding("utf-8");
		data=new String(data.getBytes("ISO-8859-1"), "UTF-8");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String,Object> mapParam = JSONUtil.jsonToMap(data);
		if((mapParam.get("borId")==null)){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "缺少借款编号参数");
			return map;
		}
		int borId = Integer.parseInt((mapParam.get("borId").toString()));
		
		
		String uid = userLoginService.ValiToken(request);
		if("".equals(uid)){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "登录信息失效");
			return map;
		}
		int nuid=Integer.parseInt(uid);
		
		Map<String,Object> mma = JSONUtil.jsonToMap(mapParam.get("sign").toString());
		if(mma.get("sid")==null||mma.get("signdata")==null){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "sid或者signdata参数");
			return map;
		}
		String sid = (mma.get("sid").toString());
		String signdata = (mma.get("signdata").toString());
		try {
			map = borService.rePay(nuid, borId, new Date(),sid,signdata);
		} catch (Exception e) {
			map.put("code", 0);
			map.put("success", false);
			map.put("message", e.getMessage());
		}

		return map;
	}

	/**
	 * 提现
	 * 
	 * @param uid
	 *            用户Id
	 * @param money
	 *            提现金额
	 * @param date
	 *            操作时间
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("api/Withdraw")
	@ResponseBody
	public Object Withdraw(@RequestBody String data,Model m,HttpSession session,HttpServletResponse resp,HttpServletRequest request) throws UnsupportedEncodingException {
		resp.setCharacterEncoding("utf-8");
		data=new String(data.getBytes("ISO-8859-1"), "UTF-8");
//		Integer uid=(Integer)session.getAttribute("userid");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String,Object> mapParam = JSONUtil.jsonToMap(data);
		if((mapParam.get("money")==null)){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "缺少金额参数");
			return map;
		}
		if((mapParam.get("sign")==null)){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "缺少签名参数");
			return map;
		}
		double money = Double.parseDouble((mapParam.get("money").toString()));
		Map<String,Object> mma = JSONUtil.jsonToMap(mapParam.get("sign").toString());
		if(mma.get("sid")==null||mma.get("signdata")==null){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "sid或者signdata参数");
			return map;
		}
		String sid = (mma.get("sid").toString());
		String signdata = (mma.get("signdata").toString());
		
		String uid = userLoginService.ValiToken(request);
		if("".equals(uid)){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "登录信息失效");
			return map;
		}
		int nuid=Integer.parseInt(uid);
		
		try {
			map = borService.Withdraw(nuid, money, new Date(),signdata,sid);
		} catch (Exception e) {
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "提现失败");
		}
		map.put("code", 200);
		return map;
	}

	/**
	 * 充值
	 * 
	 * @param uid
	 *            充值用户
	 * @param money
	 *            充值金额
	 * @param date
	 *            时间
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("api/Recharge")
	@ResponseBody
	public Object Recharge(@RequestBody String data, Model m,HttpSession session,HttpServletResponse resp,HttpServletRequest request) throws UnsupportedEncodingException {
		resp.setCharacterEncoding("utf-8");
		data=new String(data.getBytes("ISO-8859-1"), "UTF-8");
		Map<String, Object> map = new HashMap<String, Object>();
		double money=10000;
//		double money = Double.parseDouble((mapParam.get("money").toString()));
		
		String uid = userLoginService.ValiToken(request);
		if("".equals(uid)){
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "登录信息失效");
			return map;
		}
		int nuid=Integer.parseInt(uid);
		try {
			map = borService.Recharge(nuid, money, new Date());
		} catch (Exception e) {
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "充值异常");
			return map;
		}
		map.put("code", 200);
		map.put("success", true);
		map.put("message", "充值成功");
		return JSON.toJSON(map);
	}

	/**
	 * 查询所有待审核的借款
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("api/find")
	@ResponseBody
	public Object findWaitAudit(HttpServletResponse resp) {
		resp.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		// 待审核状态1
		List<Borrow> list = borService.findWaitAudit();
	//	System.out.println(JSON.toJSON(list));
		map.put("message", "查询成功");
		map.put("success", true);
		map.put("code", 200);
		map.put("data", list);
		return map;
	}

	/**
	 * 为用户提供利率 等待用户同意
	 * 
	 * @param borId
	 *            借款编号 id
	 * @param interest
	 *            利率
	 * @param date
	 *            操作时间
	 * @return
	 */
	@RequestMapping(value="audited")
	@ResponseBody
	public Object audited(int borId, double interest, String date,String flag,HttpServletResponse resp) {
		resp.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		Date d;
		try {
			d = ChangeDate.changeDate(date);
			map = borService.audited(borId, interest, d,flag);
		} catch (ParseException e) {
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "时间格式错误");
			return JSON.toJSON(map);
		}

		map.put("code", 200);
		map.put("success", true);
		map.put("message", "操作成功");
		return JSON.toJSON(map);
	}

	/**
	 * 查询所有待打包的借款
	 * 
	 * @return
	 */
	@RequestMapping("api/find1")
	@ResponseBody
	public Object findWaitPackage(HttpServletResponse resp) {
		resp.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		// 待审核状态1
		List<Borrow> list = null;
		try {
			list = borService.findWaitPackage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("message", "查询异常");
			map.put("success", false);
			map.put("code", 0);
		}
	//	System.out.println(JSON.toJSON(list));
		map.put("message", "查询成功");
		map.put("success", true);
		map.put("code", 200);
		map.put("data", list);
		return map;
	}

	/**
	 * 打包
	 * 
	 * @param borId
	 *            借款编号
	 * @param fid
	 *            产品编号
	 * @param stateDate
	 *            操作时间
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("CreatePackage")
	@ResponseBody
	@Transactional
	public Map<String,Object> CreatePackage(HttpServletRequest request,String borrowNum,String borIds,String bondName,Double bondShare,double interest,String startDate,int termOfLoan,String dueDate,double minMoney,HttpServletResponse resp) throws UnsupportedEncodingException {
		resp.setCharacterEncoding("utf-8");
		boolean result = true;
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		
		boolean success;
		String message = "packing success";
		String[] borIds2=borIds.split(",");
		bondName=new String(bondName.getBytes("ISO-8859-1"), "UTF-8");
		try {
			ProducetVO producet=new ProducetVO();
			producet.setPid(borrowNum);//债券编号
			producet.setProducet_name(bondName);//债券名称
			producet.setProducet_money(bondShare);//份额
			producet.setProducet_rate(interest);//利率
			producet.setProducet_stateNo(3);
			producet.setProducet_startDate(startDate);
			producet.setProducet_deadline(termOfLoan);//借款期限
			producet.setProducet_dateDue(dueDate);//到期日
			producet.setProducet_minMoney(minMoney);//最小投资金额
			
			 int a = fser.insertProducet(producet);
				for(String s:borIds2){
					Integer borid=Integer.parseInt(s);
					
					if(borid!=null){
						result = borService.CreatePackage(borid,producet.getPid(), new Date());			
					}
				}
				success = true;
				message = "操作成功";
			

		} catch (Exception e) {
			success = false;
			message = "操作失败";
		}
		//如果正常 能走到这里
		map.put("code", 200);
		map.put("sucess", true);
		map.put("message", message);
		return map;
	}
	

}
