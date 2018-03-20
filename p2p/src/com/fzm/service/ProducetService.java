package com.fzm.service;

import java.util.HashMap;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fzm.cgb.ProtobufBean;
import com.fzm.daoimpl.BorrowDaoImpl;
import com.fzm.daoimpl.ProducetDaoImpl;
import com.fzm.daoimpl.RecordDaoImpl;
import com.fzm.daoimpl.InvestDaoImpl;
import com.fzm.daoimpl.UserDaoImpl;
import com.fzm.daointerface.InvestIDao;
import com.fzm.entity.User;
import com.fzm.entity.BorrowVO;
import com.fzm.entity.ProducetVO;
import com.fzm.entity.InvestVO;
import com.fzm.service.BlockChainUtil;
import com.fzm.tools.JSONUtil;

/**
 * FileName: financingController.java
 * @Description : 对于在financing表在service层的代码实现并且返回到页面
 * @author 创建人 王斌
 * @date 创建时间 2017-8-2上午10:23:44
 * @version v1.0
 *
 * Modification  History:
 * Date              Author           Version        
 * -------------------------------------------------
 * 2017-8-2           王斌               1.0          
 *
 * Why & What is modified:
 */

@Service("investAction")
public class ProducetService {

	@Resource(name="producet")
	private ProducetDaoImpl fser;
	@Resource(name="invest")
	private InvestDaoImpl ufser;
	@Resource(name="user")
	private UserDaoImpl userv;
	@Resource(name="record")
	private RecordDaoImpl rserv;
	@Resource(name="borrows")
	private BorrowDaoImpl bserv;
	@Autowired
	private DataSourceTransactionManager txManager;
	@Autowired
	InvestIDao ufdao;
	
	
	
	/**
	 * 获取用户的信息
	 * @param uid 用户id
	 * */
	public Map<String,Object> userMessage(int uid){
	
		Map<String,Object> map = new HashMap<String,Object>();
		User user = new User();
		user.setUid(uid);
		List<Map<String, Object>> users = userv.getUser(user.getUid());
		map.put("code", "200");
		map.put("StateMsg","登陆成功");
		map.put("StateCode", true);
		map.put("users", users);
		return map;
	}
	
	/**
	 * 获取登陆用户所有的没有投资的理财产品
	 * @param  page 页码
	 * @param row 要显示的行数
	 * @param uid 用户id
	 * */
	public Map<String,Object> achieve(Object page,Object row,int uid){
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		if (page!=null&row!=null){
			//如果rows是null，给页面要显示的页码下标赋值
			int pageIndex=Integer.parseInt(page.toString());
			int rows=Integer.parseInt(row.toString());
			int startPageIndex = (pageIndex-1)*rows;
			List<Map<String,Object>> fList_One = fser.gainProducet(uid,startPageIndex,rows);
			int count = fser.getCounts(uid);
			map.put("code", "200");
			map.put("StateMsg","指定页码与显示条数查询债券列表");
			map.put("StateCode", true);
			map.put("fResult", fList_One);
			map.put("count", count);
			return map;
		}else{
			int pageIndex=1;
			int rows=5;
			int startPageIndex = (pageIndex-1)*rows;
			List<Map<String,Object>> fList_One = fser.gainProducet(uid,startPageIndex,rows);
			int count = fList_One.size();
			map.put("code", "200");
			map.put("StateMsg","未指定页码与显示条数查询债券列表");
			map.put("StateCode", true);
			map.put("fResult", fList_One);
			map.put("count", count);
			return map;
		}
	}
	
	
	/**
	 * 获取登陆用户所有的已经投资的理财产品
	 * @param page 页码
	 * @param row 要显示的行数
	 * @param uid 用户id
	 * @param ufsid 产品包状态
	 * */
	public Map<String,Object> achieveByFid(Object page,Object row,int uid,int ufsid){
		Map<String,Object> map = new HashMap<String,Object>();
		if (page!=null&row!=null){
			//如果rows是null，给页面要显示的页码下标赋值
			int pageIndex=Integer.parseInt(page.toString());
					int rows=Integer.parseInt(row.toString());
					int startPageIndex = (pageIndex-1)*rows;
					
					List<Map<String, Object>> fList_Two = 
							fser.gainProducetByPid(uid,startPageIndex,rows);
					int count = fser.getCount(uid);
					map.put("code", "200");
					if(ufsid==5){
						map.put("StateMsg","未指定页码与显示条数查询我的投资列表（显示已投资和已生效的债券包）");
					}
					if(ufsid==0){
						map.put("StateMsg","未指定页码与显示条数查询我的投资列表（所有投资）");
					}
					map.put("StateCode", true);
					map.put("financing",fList_Two);
					map.put("count", count);
					return map;
				}else{
					int pageIndex=1;
					int rows=5;
					int startPageIndex = (pageIndex-1)*rows;
				
					List<Map<String, Object>> fList_Two = 
						fser.gainProducetByPid(uid,startPageIndex,rows);
					int count = fList_Two.size();
					map.put("code", "200");
					if(ufsid==5){
						map.put("StateMsg","未指定页码与显示条数查询我的投资列表（显示已投资和已生效的债券包）");
					}
					if(ufsid==0){
						map.put("StateMsg","未指定页码与显示条数查询我的投资列表（所有投资）");
					}
					map.put("StateCode", true);
					map.put("financing",fList_Two);
					map.put("count", count);
					return map;
				}
	}
	
	/**
	 * 充值和提款记录
	 * @param t  类型
	 * @param  page 页码
	 * @param row 要显示的行数
	 * @param uid 用户id
	 * */
	
	public Map<String,Object> record(Object t,Object page,Object row,int uid){
		
		Map<String,Object> map = new HashMap<String,Object>();
		int count = rserv.getCount(uid);
		if(t!=null){
			if (page!=null&row!=null){
				//如果rows是null，给页面要显示的页码下标赋值
				int pageIndex=Integer.parseInt(page.toString());
				int rows=Integer.parseInt(row.toString());
				int type=Integer.parseInt(t.toString()); 
				int startPageIndex = (pageIndex-1)*rows;
				if(type==1){
					List<Map<String, Object>> rList = 
							rserv.selectRecord(uid,startPageIndex,rows);
					map.put("recharge", rList);
					map.put("count", count);
					map.put("code", 200);
					map.put("StateCode", true);
					map.put("StateMsg", "指定页码、显示条数，查询充值记录");
					return map;
				}else{
					List<Map<String, Object>> rLists = 
							rserv.selectURecord(uid,startPageIndex,rows);
					map.put("draw", rLists);
					map.put("count", count);
					map.put("code", 200);
					map.put("StateCode", true);
					map.put("StateMsg", "指定显示类型、页码、显示条数，查询提现记录");
				return map;
				}
			}else{
				int type=Integer.parseInt(t.toString()); 
				if(type==1){
					int pageIndex=1;
					int rows=5;
					int startPageIndex = (pageIndex-1)*rows;
					List<Map<String, Object>> rList = 
							rserv.selectRecord(uid,startPageIndex,rows);
					map.put("recharge", rList);
					map.put("count", count);
					map.put("code", 200);
					map.put("StateCode", true);
					map.put("StateMsg", "指定显示类型，但没有指定页码、显示条数，查询充值记录");
					return map;
				}else{
					int pageIndex=1;
					int rows=5;
					int startPageIndex = (pageIndex-1)*rows;
					List<Map<String, Object>> rLists = 
							rserv.selectURecord(uid,startPageIndex,rows);
					map.put("draw", rLists);
					map.put("count", count);
					map.put("code", 200);
					map.put("StateCode", true);
					map.put("StateMsg", "指定显示类型，但没有指定页码、显示条数，查询提现记录");
					return map;
				}
			}
		}else{
			int pageIndex=1;
			int rows=5;
			int startPageIndex = (pageIndex-1)*rows;
			List<Map<String, Object>> rList = 
					rserv.selectRecord(uid,startPageIndex,rows);
			map.put("recharge", rList);
			map.put("count", count);
			map.put("code", 200);
			map.put("StateCode", true);
			map.put("StateMsg", "没有指定显示类型、页码、显示条数，默认查询充值记录");
			return map;
		}
	}
	
	/**
	 * 首先将需要更新的理财产品的状态更新到状态更新表中，然后再执行更新操作
	 * @param uid 用户的id
	 * */
	@Transactional(rollbackFor=Exception.class)
	public synchronized Map<String, Object> update(double investMoney,int uid,String producetid,String qianming){
		
		Map<String, Object> map = new HashMap<String, Object>();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = txManager.getTransaction(def);
		boolean FLAG;
		try{
			ProducetVO producet = new ProducetVO();
			producet = fser.findProducetList(producetid);
			if(investMoney>=producet.getProducet_minMoney()){
				FLAG = this.updateUserMoney(investMoney, uid, producetid);
				if(FLAG){
					FLAG = this.updateFinancingMessage(investMoney, uid, producetid, qianming);
					if(FLAG){
						txManager.commit(status);
						map.put("code", 200);
						map.put("StateCode", true);
						map.put("StateMsg", "投资成功");
						return map;
					}else{
						txManager.rollback(status);
						map.put("code", 0);
						map.put("StateCode", false);
						map.put("StateMsg", "投资失败");
						return map;
					}
				}else{
					txManager.rollback(status);
					map.put("code", 0);
					map.put("StateCode", false);
					map.put("StateMsg", "投资失败");
					return map;
				}
			}else{
				txManager.rollback(status);
				map.put("code", 0);
				map.put("StateCode", false);
				map.put("StateMsg", "投资失败");
				return map;
			}
		}catch(Exception e){
			txManager.rollback(status);
			map.put("code", 0);
			map.put("StateCode", false);
			map.put("StateMsg", "投资失败");
			return map;
		}
	}
	
	/**
	 * 修改用户的资金以及投资状态
	 * @param iMoney  投资金额
	 * @param uid	用户id
	 * @param fid   产品编号
	 * */
	public boolean updateUserMoney(double iMoney,int uid,String fid){
		
		User uvo = new User();
		InvestVO ivo= new InvestVO();
		ProducetVO producet = new ProducetVO();
		
		//获取用户信息
		uvo = userv.findUserList(uid);
		//获取
		producet = fser.findProducetList(fid);
		
		ivo.setInvest_uid(uid);
		ivo.setInvest_producet_id(fid);
		if(ufser.getCountById(ivo) == 0){
			double ieRevenue = Math.floor(
						iMoney*100*producet.getProducet_rate()*10000*producet.getProducet_deadline()/360
					)/1000000+iMoney;
			double coin = producet.getProducet_money()-producet.getProducet_amount();
			//判断用户充值的金额是否大于自己的账户余额,是否大于整个产品未融资的金额
	 
			if(uvo.getBalance() > iMoney||uvo.getBalance()==iMoney & coin >= iMoney){
				//修改用户的账户余额
				uvo.setBalance(uvo.getBalance()-iMoney);
				uvo.setBondHoldings(uvo.getBondHoldings()+iMoney);
				//判断修改用户余额是否修改成功
				if(userv.updateMoney(uvo)>0){
					double scale=Math.floor(iMoney/producet.getProducet_money()*10000)/10000;
					
					ivo.setInvest_producet_id(fid);
					ivo.setInvest_uid(uid);
					ivo.setInvestMoney(iMoney);
					ivo.setInvest_eRevenue(ieRevenue);
					ivo.setInvest_scale(scale);
					ivo.setInvest_stateNo(3);
					if(ufser.insert(ivo)>0){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * 对产品包的信息进行更新
	 * @param iMoney  投资金额
	 * @param uid	用户id
	 * @param producetid   产品编号
	 * @param qianming  用户签名数据
	 * */
	HttpServletRequest req;
	
	public boolean updateFinancingMessage(double iMoney,int uid,String producetid,String qianming){
		
		User uvo = new User();
		InvestVO ivo= new InvestVO();
		ProducetVO producet = new ProducetVO();
		
		producet = fser.findProducetList(producetid);
		double amounts = producet.getProducet_amount();
		
		//修改理财产品的融资金额
		producet.setProducet_amount(amounts+iMoney);
		
		//判断修改融资金额是否成功
		if(fser.UpdAmount(producet)>0){
			
			//投资金额修改成功
			double raise = Math.floor((amounts+iMoney)/producet.getProducet_money()*100)/100;
			producet.setProducet_raiseprocess(raise);
			fser.updRaise(producet);
			
			//判断融资金额是否和投资金额相等
			if(producet.getProducet_amount() == producet.getProducet_money()){
				
				 /*	融资金额是否和投资金额相等
				  * Start
				  */
				producet.setProducet_stateNo(4);
				producet.setProducet_raiseprocess(1);
				fser.updRaise(producet);
				if(fser.updateProducetByPid(producet)>0){
					//状态修改成功
					producet = fser.findProducetList(producetid);
					if(producet.getProducet_stateNo()==4){
					 /*
					  * 给借款人打钱
					  * Start
					  * */
						ivo.setInvest_stateNo(4);
						ivo.setInvest_producet_id(producetid);
						ivo.setInvest_uid(uid);
						ufser.updUfsid(ivo);
						List<Integer> borIdList = bserv.findBorrowBorIdList(producetid);
						
						for(int i : borIdList){
							BorrowVO bvo = bserv.findBorrowBorMoneyInfo(i);
							uvo = userv.findUserList(bvo.getBorrow_uid());
							uvo.setBalance(uvo.getBalance()+bvo.getBorMoney());
							uvo.setTotalAssets(uvo.getBalance());
							userv.updateMoney(uvo);
						}
					 /*
					  * 给借款人打钱
					  * End
					  * */
						
				     /*
					  * 将数据转发到区块链中
					  * Start
					  * */
						ProtobufBean protobufBean = new ProtobufBean();
				  		Map<String,Object> mma = JSONUtil.jsonToMap(qianming);
				  		long instructionId = Long.parseLong(mma.get("sid").toString());
				  		protobufBean.setInstructionId(instructionId);
				  		protobufBean.setSignature(mma.get("signdata").toString());
				  		String result = BlockChainUtil.sendPostParam(protobufBean);
				  		System.out.println("区块链反馈过来的结果："+result);
				  		boolean flag = BlockChainUtil.vilaResult(result);
				  		if(flag){
				  			ivo.setInvest_producet_id(producetid);
				  			ivo.setInvest_uid(uid);
				  			ivo.setHash(String.valueOf(instructionId));
				  			ufdao.updHash(ivo);
							return true; 
				  		}else{
							return false;
						}
				     /*
					  * 将数据转发到区块链中
					  * End
					  * */
					}else{
						return false;
					}
				}else{
					return false;
				}
			}
			 /*   融资金额和投资金额相等
			  *   End
			  */
			else{
				/*
				 * 融资金额和投资金额不相等
				 * 转发到区块链中
				 * Start
				 * */
				ProtobufBean protobufBean = new ProtobufBean();
				Map<String,Object> mma = JSONUtil.jsonToMap(qianming);
		  		long instructionId = Long.parseLong(mma.get("sid").toString());
		  		protobufBean.setInstructionId(instructionId);
		  		protobufBean.setSignature(mma.get("signdata").toString());
		  		String result = BlockChainUtil.sendPostParam(protobufBean);
		  		System.out.println("区块链反馈过来的结果："+result);
		  		boolean flag = BlockChainUtil.vilaResult(result);
		  		if(flag){
		  			ivo.setInvest_producet_id(producetid);
		  			ivo.setInvest_uid(uid);
		  			ivo.setHash(String.valueOf(instructionId));
		  			ufdao.updHash(ivo);
					return true;	
		  		}else{
					return false;	 
		  		}
			}
			/*
			 * 融资金额和投资金额不相等
			 * End
			 * */
		}else{
			return false;
		}
	}
}