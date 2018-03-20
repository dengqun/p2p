package com.fzm.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
import com.fzm.cgb.HexUtil;
import com.fzm.cgb.KeyUtils;
import com.fzm.cgb.ProtobufBean;
import com.fzm.cgb.ProtobufUtils;
import com.fzm.cgb.RequestAccountBean;
import com.fzm.cgb.Response;
import com.fzm.cgb.Result;
import com.fzm.dao.AttachmentDao;
import com.fzm.dao.BorrowDao;
import com.fzm.dao.BorrowInter;
import com.fzm.dao.FinancingDao;
import com.fzm.dao.RecordDao;
import com.fzm.dao.StateDao;
import com.fzm.dao.UserDao;
import com.fzm.daoimpl.BorrowDaoImpl;
import com.fzm.daoimpl.ProducetDaoImpl;
import com.fzm.daoimpl.InvestDaoImpl;
import com.fzm.daoimpl.UserDaoImpl;
import com.fzm.daointerface.BorrowIDao;
import com.fzm.daointerface.InvestIDao;
import com.fzm.daointerface.UserIDao;
import com.fzm.entity.AttachmentVO;
import com.fzm.entity.Borrow;
import com.fzm.entity.BorrowVO;
import com.fzm.entity.Financing;
import com.fzm.entity.ProducetVO;
import com.fzm.entity.RecordVO;
import com.fzm.entity.InvestVO;
import com.fzm.entity.User;
import com.fzm.http.HttpRequest;
import com.fzm.tools.ConfigReader;
import com.fzm.tools.Page;

/**
 * 
 * @Description : 处理借款业务逻辑
 * @author sdy
 * @date 2017-08-01
 * 
 */
@Service
public class BorService {

	@Autowired
	UserDao userDao;
	@Autowired
	BorrowDao borrowDao;
	@Autowired
	StateDao stateDao;
	@Autowired
	FinancingDao financingDao;
	@Autowired
	RecordDao recordDao;
	@Resource(name="invest")
	private InvestDaoImpl ufser;
	@Resource(name="producet")
	private ProducetDaoImpl fserv;
	@Resource(name="user")
	private UserDaoImpl userv;
	@Resource(name="borrows")
	private BorrowDaoImpl bserv;
	@Autowired
	AttachmentDao attachmentDao;
	@Autowired
	BorrowInter borrowInter;
	@Autowired
	BorrowIDao bdao;
	@Autowired
	UserIDao udao;
	@Autowired
	InvestIDao ufdao;
	
	public static HttpRequest httpRequest = new HttpRequest();
	private static final Logger logger = Logger.getLogger(BorService.class); 

	/**
	 * 借款主页
	 * 
	 * @param uid
	 *            用户Id
	 * @return 用户信息
	 */

	public User BorMain(int uid) {
		User user = new User();
		user.setUid(uid);
		user = userDao.findById(user); // 查询用户信息
		List<Borrow> list = borrowDao.getTotalBor(user); // 通过用户id查询用户的借款
		
		user.setBorrow(list);

		return user;

	}
	
	public Page<Borrow> queryBorrowList(int currentPage,int pageSize,int uid) {
		User user = new User();
		user.setUid(uid);
		Page pe=new Page(currentPage,pageSize);
		System.out.println(pe.getBeginPageIndex());
		user.setBeginPageIndex(pe.getBeginPageIndex());
		user.setPageSize(pageSize);
		List<Borrow> list = borrowInter.queryBorrowList(user); // 通过用户id查询用户的借款
		int count = borrowInter.queryAllBorrowList(user);
		return new Page(currentPage,pageSize,count, list);

	}
	
	public Page<Borrow> queryRechargeRecord(int uid,int currentPage,int pageSize) {
		User user = new User();
		user.setUid(uid);
		Page pe=new Page(currentPage,pageSize);
		System.out.println(pe.getBeginPageIndex());
		user.setBeginPageIndex(pe.getBeginPageIndex());
		user.setPageSize(pageSize);
		List<RecordVO> list = recordDao.queryRechargeRecord(user); // 通过用户id查询用户的借款
		int count=recordDao.queryAllRechargeRecord(user);
		return new Page(currentPage,pageSize,count, list);

	}
	
	public Page<Borrow> queryExtractList(int uid,int currentPage,int pageSize) {
		User user = new User();
		user.setUid(uid);
		Page pe=new Page(currentPage,pageSize);
		System.out.println(pe.getBeginPageIndex());
		user.setBeginPageIndex(pe.getBeginPageIndex());
		user.setPageSize(pageSize);
		List<RecordVO> list = recordDao.queryExtractList(user); // 通过用户id查询用户的借款
		int count=recordDao.queryAllExtractList(user);
		return new Page(currentPage,pageSize,count, list);

	}
	
	public User queryBorrowUser(int uid) {
		User user = new User();
		user.setUid(uid);
		user = userDao.findById(user); // 查询用户信息
		return user;
	}

	/**
	 * 同意利率
	 * 
	 * @param d
	 *            申请日期
	 * @param borId
	 *            借款Id
	 * @param interest
	 *            利率
	 * @return
	 */
	@Transactional
	public Map<String, Object> acceptApplyFor(Date d, int borId,int status,String sid,String signdata) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean success = true;
		String message = "操作成功";
		Borrow borrow = new Borrow();
		borrow.setBorId(borId);
		borrow = borrowDao.findById(borrow);
		if(borrow==null){
			map.put("success", false);
			map.put("code", 0);
			map.put("message", "交易不存在");
			return map;
		}
		
		
		ProtobufBean protobufBean = new ProtobufBean();
  		protobufBean.setInstructionId(Long.parseLong(sid));
  		protobufBean.setSignature(signdata);
  		String result = BlockChainUtil.sendPostParam(protobufBean);
  		logger.warn("borrow accept rate result data::::::::::::::----->"+result);
  		boolean a = BlockChainUtil.vilaResult(result);
  		if(a){	//成功
		}else{
		 throw new RuntimeException("借款同意利率异常");
		}
//  		Result r1=JSON.parseObject(result, Result.class);
//		 if(!(r1.getError().isEmpty())){
//			 logger.warn("block chain error accept rate info!!!!!!!!!!!!!!!!!!!!:"+r1.getError() );
//			throw new RuntimeException("accept rate block chain error(同意利率区块链错误)");
//		 }else{
//			 Response response=JSON.parseObject((r1.getResult().get(1)).toString(),Response.class);
//			 if(response.getCode()==1){
//				 logger.warn("accept rate block chain exception info@@@@@@@@@@@@@@@@@@:"+HexUtil.hexToString(response.getData()) );
//				 throw new RuntimeException("accept rate block chain exception(同意利率区块链异常)"+HexUtil.hexToString(response.getData()));
//			 }else{
//				 logger.warn("accept rate block chain success info:"+HexUtil.hexToString(response.getData()) );
//			 }
//		 }
		 
		if(status==0){
			borrow.setBorState(7); // 修改借款状态为拒绝
			int uid = bdao.selUid(borId);
			User uvo = udao.selAll(uid);
			uvo.setAvailableCredit(uvo.getAvailableCredit()+borrow.getBorMoney());
			uvo.setBorTotal(uvo.getBorTotal()-borrow.getBorMoney());
			uvo.setReTotal(uvo.getReTotal()-borrow.getBorrow_repayMoney());
			udao.updBorrow(uvo);
			
		}else{
			borrow.setBorState(3); 	// 修改借款状态为待打包
			
		}
									

		if (borrowDao.updateBor(borrow) > 0) {
			this.addState(borrow.getBorrow_uid(), 2, borrow.getBorId(), d, "操作成功");// 添加记录到状态表

		} else {
			this.addState(borrow.getBorrow_uid(), 2, borrow.getBorId(), d, "操作失败");// 添加记录到状态表
			map.put("code", 0);
			map.put("success", 200);
			map.put("message", "更新借款状态失败");
			return map;
		}
		map.put("code", 200);
		map.put("success", true);
		map.put("message", message);
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
	 * @throws Exception
	 */
	@Transactional
	public Map<String, Object> applyFor(int uid, double interest,
			double borMoney, String borUse, String instalment,
			String mortgageNo, int borDeadline,String attachmentid,String repaymodel,String sid,String sign) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		boolean success = true;
		String message = "操作成功";

		User user = new User();
		user.setUid(uid);
		Borrow borrow = new Borrow();
		borrow.setBorrow_interest(interest/100);
		 logger.warn("borrow interest 2222222222222222:"+borrow.getBorrow_interest());
		borrow.setBorDate(new Date());
		borrow.setBorMoney(borMoney);
		borrow.setBorUse(borUse);
		borrow.setBorrow_instalment(instalment);
		borrow.setBorrow_mortgageNo(mortgageNo);
		borrow.setBorDeadline(borDeadline);
		borrow.setBorrow_repaymodel(repaymodel);
		double repay = borrow.getBorMoney()*100*interest*100/360*borDeadline;
		double repaymoney = repay/1000000+borrow.getBorMoney();
		
		borrow.setBorrow_repayMoney(repaymoney);
		user = userDao.findById(user); // 查詢用戶信息
        logger.warn("borrow user credit>>>>>>>>"+user.getAvailableCredit());
        logger.warn("borrow user bormoney<<<<<<<<<"+borrow.getBorMoney());
		if (user.getAvailableCredit() < borrow.getBorMoney()) { // 比较借款金额与可借余额
			message = "可借余额不足";
			success = false;
			map.put("code", 0);
			map.put("success", success);
			map.put("message", message);
			return map;
		} else {

			borrow.setBorrow_uid(user.getUid()); // 设置借款人id
			borrow.setBorrow_name(user.getName());
			borrow.setBorState(1); // 设置借款状态为1，待审核
			borrow.setHash(sid);
			 logger.warn("borrow interest 3333333333333:"+borrow.getBorrow_interest());
            int count=borrowDao.applyFor(borrow);
          
			if ( count > 0) { // 添加借款申请信息

				user.setAvailableCredit(user.getAvailableCredit()
						- borrow.getBorMoney()); // 更新可借款余额
				user.setBorTotal(user.getBorTotal() + borrow.getBorMoney());// 更新已借余额
				user.setReTotal(user.getReTotal() + repaymoney);
//				boolean flag1=this.sendPostSign(sign);
//		    	System.out.println("block chain apply borrow return flag:"+flag1);
//		    	if(!flag1){
//		    		throw new Exception("block chain borrow apply exception");	
//		    	}
				ProtobufBean protobufBean = new ProtobufBean();
		  		protobufBean.setInstructionId(Long.parseLong(sid));
		  		protobufBean.setSignature(sign);
		  		String result = BlockChainUtil.sendPostParam(protobufBean);
		  		logger.warn("borrow sign data::::::::::::::----->"+sign);
		  		logger.warn("borrow blockchain result data::::::::::::::----->"+result);
		  		boolean a = BlockChainUtil.vilaResult(result);
		  		if(a){	//成功
				}else{
				 throw new RuntimeException("借款申请区块链异常");
				}
		  		
//				 if(!(r1.getError().isEmpty())){
//					 logger.warn("block chain error info!!!!!!!!!!!!!!!!!!!!:"+r1.getError() );
//					throw new RuntimeException("借款申请区块链错误,存在重复合同编号");
//				 }else{
//					 Response response=JSON.parseObject((r1.getResult().get(1)).toString(),Response.class);
//					 if(response.getCode()==1){
//						 logger.warn("apply borrow block chain exception info@@@@@@@@@@@@@@@@@@:"+HexUtil.hexToString(response.getData()) );
//						 throw new RuntimeException("借款申请区块链异常"+HexUtil.hexToString(response.getData()));
//					 }else{
//						 logger.warn("apply borrow block chain success info:"+HexUtil.hexToString(response.getData()) );
//					 }
//				 }
			
					if (userDao.updateUserAsset(user) > 0) { // 更新用户资产

					} else {

						throw new RuntimeException("更新用户资产失败");	//抛出异常事务回滚
					}
			

				
			} else {
				map.put("code", 0);
				message = "提交借款申请失败";
				success = false;
				throw new RuntimeException("更新用户资产失败");
			}
		}
		
		//处理附件
		if(attachmentid!=null){
			String[] aid=attachmentid.split(",");
			AttachmentVO attach=new AttachmentVO();
			for(int j=0;j<aid.length;j++){
				attach.setAttachmentid(aid[j]);
				attach.setRelationid(String.valueOf(borrow.getBorId()));
				attachmentDao.updateAttachment(attach);
			}
			attach=null;
		}
		
		//结束对附件的处理
		map.put("code", 200);
		map.put("success", success);
		map.put("message", message);
		return map;
	}

	public String uploadFile(MultipartFile[] files,HttpServletRequest request,String uid){
//		String uid = (String)request.getSession().getAttribute("userid");
		String attachmentid="";
		//处理附件开始
		 for(int i = 0;i<files.length;i++){  
			 AttachmentVO attach=new AttachmentVO();
               MultipartFile file = files[i];  
               if (!file.isEmpty()) {  
   	        	String uuid=UUID.randomUUID().toString().replace("-","");
   	        	String prefix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
   	            try {  
   	            	
   	                String filePath = request.getSession().getServletContext().getRealPath("/") + "/fileUpload/"  
   	                        + uuid+"."+prefix;  
   	                file.transferTo(new File(filePath));  
   	                AttachmentVO attachment=   new AttachmentVO();
   	                attachment.setUrl(request.getContextPath()+"/fileUpload/"+ uuid+"."+prefix);
   	                attachment.setCreatetime(new Date());
   	                attachment.setCreateuser(uid);
   	                attachment.setType(prefix);
   	                attachment.setName(file.getOriginalFilename());
   	                attachment.setStatus("1");
   	            attachmentDao.insertAttachment(attachment);
   	           if("".equals(attachmentid)){
     	             attachmentid=String.valueOf(attachment.getId());
   	           }else{
     	             attachmentid=attachmentid+","+String.valueOf(attachment.getId());
   	           }
   	            } catch (Exception e) {  
   	                e.printStackTrace();  
   	            }  
   	        }   
               
               
           }  
		return attachmentid;
		//处理附件结束
	}
	   
	/**
	 * 还款
	 * 
	 * @param uid
	 *            用户Id
	 * @param borId
	 *            借款Id
	 * @param date
	 *            还款时间
	 *            2200
	 * @return
	 */
	@Transactional
	public Map<String, Object> rePay(int uid, int borId, Date date,String sid,String signdata) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean success = true;
		String message = "操作成功";
		User user = new User();
		user.setUid(uid);
		Borrow borrow = new Borrow();
		borrow.setBorId(borId);
		borrow = borrowDao.findById(borrow);

		double reTotal = borrow.getBorrow_repayMoney();// 计算还款金额

		user = userDao.findById(user);// 查看用户金额
		if (reTotal <= user.getBalance()) { // 判断用户金额是否足够还钱

			user.setBalance(user.getBalance() - reTotal); // 修改余额
			user.setTotalAssets(user.getBalance()+user.getBondHoldings()); // 修改总资产
//			user.setAvailableCredit(user.getAvailableCredit() + reTotal);// 修改可用信用额
			user.setAvailableCredit(user.getAvailableCredit() + borrow.getBorMoney()); // 修改可用信用额
			user.setBorTotal(user.getBorTotal() - borrow.getBorMoney());// 修改已借款额
			user.setReTotal(user.getReTotal() - reTotal);//待还总额
			try {
				if (userDao.updateBalance(user) > 0) {

				} else {
					map.put("code", 0);
					map.put("success", false);
					map.put("message", "更新用户余额失败");
					return map;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (userDao.updateUserAsset(user) > 0) {

			} else {
				map.put("code", 0);
				map.put("success", false);
				map.put("message", "更新用户资产失败");
				return map;
			}

			borrow.setBorState(6);// 更新用户借款单状态为已完成
			borrow.setBorrow_almoney(reTotal);
			if (borrowDao.updateBorState(borrow) > 0) {
				this.addState(uid, 5, borId, date, "操作成功");
			} else {

				this.addState(uid, 5, borId, date, "操作失败");
				map.put("code", 0);
				map.put("success", false);
				map.put("message", "更新用户借款状态失败");
				return map;
			}

			
			String fid ="";
			try {
				fid = financingDao.findFinByBor(borrow);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map.put("code", 0);
				map.put("success", true);
				map.put("message", "还款没有相应产品");
				return map;
			}
			
			//王斌加的       start
			ProducetVO producet = new ProducetVO();
			Financing financing = new Financing();
			User users = new User();
			InvestVO ivo = new InvestVO();
			producet.setPid(fid);
			financing.setPid(fid);
			producet = fserv.findProducetList(fid);
			
			//计算打给投资人的资金总和
			double rate = producet.getProducet_rate()*
					producet.getProducet_deadline()/360;//产品利息
			
			double money = borrow.getBorMoney()+
					Math.floor(borrow.getBorMoney()*rate*100)/100;//计算已经还款的金额（扣除平台费用）
			
			financing.setProducet_eRevenue(producet.getProducet_eRevenue()+money);
			financingDao.updateErevenue(financing);
			//把钱打给投资人的回笼资金里面
			List<Integer> uidList= ufser.select(fid);
			// -----------------为用户充值           后期可以删除  start
			/**
			 * 修改投资人余额和资产总额
			 * */
			//判断有多少条未还款的记录  大于0就不走
			if(bserv.findBorrowInfo(fid)==0){
				producet.setPid(fid);
				producet = fserv.findProducetList(producet.getPid());
				for(int i:uidList){
					ivo.setInvest_producet_id(producet.getPid());
					ivo.setInvest_uid(i);
					ivo =  ufser.selectUF(ivo);
					ivo.setInvest_stateNo(5);
					ufser.updUfsid(ivo);
					users = userv.findUserList(ivo.getInvest_uid());
					users.setUid(ivo.getInvest_uid());
					users.setBalance(users.getBalance()+ivo.getInvest_eRevenue());
					users.setBondHoldings(users.getBondHoldings()-ivo.getInvestMoney());
					users.setTotalAssets(users.getBalance()+users.getBondHoldings());
					userv.updateMoney(users);
				}
				producet.setProducet_stateNo(5);
				fserv.updateProducetByPid(producet);
				map.put("code", 200);
				map.put("success", true);
				map.put("message", "项目完成");
			}else{
				map.put("code", 200);
				map.put("success", true);
				map.put("message", "你已还款,等待其他用户还款");
//				return map;
			}
		//-------------为用户充值结束        后期可以删除   end		
		} else {
			map.put("code", 0);
			map.put("success", true);
			map.put("message", "用户余额不足");
			return map;
		}
		
		ProtobufBean protobufBean = new ProtobufBean();
  		protobufBean.setInstructionId(Long.parseLong(sid));
  		protobufBean.setSignature(signdata);
  		String result = BlockChainUtil.sendPostParam(protobufBean);
  		logger.warn("repay money block chain result data:"+result );
  		boolean a = BlockChainUtil.vilaResult(result);
  		if(a){	//成功
		}else{
		 throw new RuntimeException("还款区块链异常");
		}
  		
//  		Result r1=JSON.parseObject(result, Result.class);
//		 if(!(r1.getError().isEmpty())){
//			 logger.warn("repay money block chain error info:"+r1.getError() );
//			 this.record(uid, date, reTotal, "还款失败","还款","失败",null);
//			throw new RuntimeException("repay money block chain error");
//		 }else{
//			 Response response=JSON.parseObject((r1.getResult().get(1)).toString(),Response.class);
//			 if(response.getCode()==1){
//				 logger.warn("repay money block chain exception info:"+HexUtil.hexToString(response.getData()) );
//				 this.record(uid, date, reTotal, "还款失败","还款","失败",null);
//				 throw new RuntimeException("repay money block chain exception");
//			 }else{
//				 this.record(uid, date, reTotal, "还款成功","还款","成功",sid);
//				 logger.warn("repay money block chain success info:"+HexUtil.hexToString(response.getData()) );
//			 }
//		 }
		 
		 
		return map;
		
		
	}

	/**
	 * 提现
	 * 
	 * @param uid
	 * @param money
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public Map<String, Object> Withdraw(int uid, double money, Date date,String sign,String sid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean success = true;
		String message = "操作成功";

		User user = new User();
		user.setUid(uid);
		user = userDao.findById(user);

		if (user.getBalance() >= money) {
			user.setBalance(user.getBalance() - money);
			user.setTotalAssets(user.getBalance()+user.getBondHoldings());
			
				if (userDao.updateBalance(user) > 0) {
					
					ProtobufBean protobufBean = new ProtobufBean();
			  		protobufBean.setInstructionId(Long.parseLong(sid));
			  		protobufBean.setSignature(sign);
			  		String result = BlockChainUtil.sendPostParam(protobufBean);
			  		
			  		logger.warn("withdraw block chain result data:"+result );
			  		boolean a = BlockChainUtil.vilaResult(result);
			  		if(a){	//成功
					}else{
						 this.record(uid, date, money, "提现失败","提现","失败",null);
						 throw new RuntimeException("withdraw block chain exception");
					}
//			  		Result r1=JSON.parseObject(result, Result.class);
//					 if(!(r1.getError().isEmpty())){
//						 logger.warn("block chain error info:"+r1.getError() );
//						 this.record(uid, date, money, "提现失败","提现","失败",null);
//						throw new RuntimeException("withdraw block chain error");
//					 }else{
//						 Response response=JSON.parseObject((r1.getResult().get(1)).toString(),Response.class);
//						 if(response.getCode()==1){
//							 logger.warn("withdraw block chain exception info:"+HexUtil.hexToString(response.getData()) );
//							 this.record(uid, date, money, "提现失败","提现","失败",null);
//							 throw new RuntimeException("withdraw block chain exception");
//						 }else{
//							 logger.warn("withdraw block chain success info:"+HexUtil.hexToString(response.getData()) );
//						 }
//					 }
					this.record(uid, date, money, "提现成功","提现","已完成",sid);
				} else {
					this.record(uid, date, money, "提现失败","提现","失败",null);
					map.put("code", 0);
					map.put("success", false);
					map.put("message", "更改用户余额失败");
					return map;
				}
			
		} else {
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "用户余额不足");
			return map;
		}
		map.put("success", success);
		map.put("message", message);
		return map;
	}

	/**
	 * 充值
	 * 
	 * @param uid
	 *            充值用户Id
	 * @param money
	 *            充值金额
	 * @param date
	 *            充值时间
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public Map<String, Object> Recharge(int uid, double money, Date date) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean success = true;
		String message = "操作成功";

		User user = new User();
		user.setUid(uid);
		user = userDao.findById(user);
		user.setBalance(user.getBalance() + money);
		user.setTotalAssets(user.getBalance()+user.getBondHoldings());
		if (userDao.updateBalance(user) > 0) {
			ProtobufBean protobufBean = this.RechargeService(uid,String.valueOf(money));
			String result = BlockChainUtil.sendPostParam(protobufBean);
			 logger.warn("recharge block chain result data:--->"+result);
			boolean a = BlockChainUtil.vilaResult(result);
	  		if(a){	//成功
	  			map.put("code", 200);
				map.put("success", success);
				map.put("message", message);
				this.record(uid, date, money, "充值成功","充值","已完成",String.valueOf(protobufBean.getInstructionId()));
			}else{
				map.put("code", 0);
				map.put("success", false);
				map.put("message", "充值区块链失败");
				this.record(uid, date, money, "充值失败","充值","失败",null);
			 throw new RuntimeException("充值失败区块链异常");
			}		
		} else {
			this.record(uid, date, money, "充值失败","充值","失败",null);
			map.put("code", 0);
			map.put("success", false);
			map.put("message", "更改用户余额失败");
			return map;
		}
		map.put("success", success);
		map.put("message", message);
		return map;
	}

	/**
	 * 查询所有待审核的借款
	 * 
	 * @param borrow
	 * @return
	 */
	public List<Borrow> findWaitAudit() {
		Borrow borrow = new Borrow();
		borrow.setBorState(1); // 待审核状态1
		List<Borrow> list = borrowDao.findByState(borrow);

		return list;
	}

	/**
	 * 审核
	 */

	/**
	 * 查询所有待打包的借款
	 * 
	 * @param borrow
	 * @return
	 */
	public List<Borrow> findWaitPackage() {
		Borrow borrow = new Borrow();
		borrow.setBorState(3); // 待打包状态3
		List<Borrow> list = borrowDao.findByState(borrow);

		return list;
	}

	/**
	 * 打包
	 * 
	 * @param borId
	 *            借款编号borId
	 * @param fid
	 *            产品编号fid
	 * @param stateDate
	 *            打包时间
	 * @return
	 */
	
	public boolean CreatePackage(int borId, String fid, Date stateDate) {
		
		boolean result = true;
		Borrow bor = new Borrow();
		bor.setBorId(borId);
		bor = borrowDao.findUidByBorId(bor);
		bor.setBorState(4);
		bor.setBorrow_startDate(stateDate);
		if (borrowDao.updateBorState(bor) > 0) {// 修改借款状态为已发行
			this.addState(bor.getBorrow_uid(), 3, borId, stateDate, "操作成功");
		} else {
			this.addState(bor.getBorrow_uid(), 3, borId, stateDate, "操作失败");
			return false;
		}
		if (borrowDao.updateStartDate(bor) > 0) {// 修改借款状态为已发行

		} else {

			return false;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("borId", borId);
		map.put("fid", fid);
		if (borrowDao.CreateB_Financing(map) > 0) {

		} else {
			result = false;
		}
		return result;
	}

	/**
	 * 添加状态
	 * 
	 * @param uid
	 * @param sta
	 * @param borId
	 * @param stateDate
	 * @param action
	 */
	private void addState(int uid, int sta, int borId, Date stateDate,
			String action) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("stateNo", sta);
		map.put("borId", borId);
		map.put("stateDate", stateDate);
		map.put("action", action);
		if (stateDao.insertBor(map) > 0) {

		}

	}

	/**
	 * 充值 提现记录
	 */
	public void record(int uid, Date date, double rMoney, String action,String type,String status,String hash) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("rechargeDate", date);
		map.put("rMoney", rMoney);
		map.put("action", action);
		map.put("type", type);
		map.put("status", status);
		map.put("hash", String.valueOf(hash));
		//map.put("fulfilDate", new Date());
		recordDao.insert(map);
	}

	/**
	 * 修改借款状态为已审核，并给予利率
	 * 
	 * @param borId
	 * @param interest
	 * @return
	 */
	@Transactional
	public Map<String, Object> audited(int borId, double interest, Date date,String flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean success = true;
		String message = "操作成功";
		Borrow bor = new Borrow();
		bor.setBorId(borId);
		bor.setBorState(2);
		bor.setBorrow_interest(interest);
		if (borrowDao.updateBor(bor) > 0) {
			BorrowVO vo= new BorrowVO(); 
			bor = borrowDao.findById(bor);
			double repay = bor.getBorMoney()*100*interest*10000/360*bor.getBorDeadline();
			double repaymoney = repay/1000000+bor.getBorMoney();
			vo.setBorId(borId);
			vo.setBorrow_repayMoney(repaymoney);
			bdao.updateRepayMoney(vo);
			this.addState(bor.getBorrow_uid(), 1, borId, date, "操作成功");
		} else {
			map.put("success", false);
			map.put("code", 0);
			map.put("message", "操作失败");
			return map;
		}
		if("1".equals(flag)){
			Borrow bo=new Borrow();
			bo.setBorId(borId);
			bo.setBorState(7);
			borrowDao.updateBor(bo);
			int uid = bdao.selUid(borId);
			User uvo = udao.selAll(uid);
			bor = borrowDao.findById(bor);
			uvo.setAvailableCredit(uvo.getAvailableCredit()+bor.getBorMoney());
			uvo.setBorTotal(uvo.getBorTotal()-bor.getBorMoney());
			uvo.setReTotal(uvo.getReTotal()-bor.getBorrow_repayMoney());
			udao.updBorrow(uvo);
		}
		map.put("code", 200);
		map.put("success", success);
		map.put("message", message);
		return map;
	}		
	
	public ProtobufBean RechargeService(int uid,String rMoney) throws FileNotFoundException{
		ProtobufBean protobufBean = new ProtobufBean();
		User user=new User();
		user.setUid(uid);
		User uinfo=userDao.findById(user);
		System.out.println("已经在本地注册过的用户的信息:"+user);
		//将用户注册信息存放一份到区块链
		if(user != null){
			System.out.println("同时将信息记录到区块链……");
			String randomNumber = uinfo.getRandomNumber();//随机数
			 logger.warn("用户随机数:"+randomNumber);
			 logger.warn("用户密码:"+uinfo.getPassword());
			String privateKey = KeyUtils.getPrivateKey(uinfo.getPassword(), randomNumber);	//私钥
			String pubkey = KeyUtils.getPublicKey(privateKey);	//公钥
			System.out.println("充值用户公钥："+pubkey);
			System.out.println("充值用户id："+uid);
			 logger.warn("借款金额:"+rMoney);
		//	System.out.println("用户类型："+type);
//			ProtobufUtils.requestUserCreate(operatorPrivateKey, pubkey, userUid, userPubkey, info);
			String operatorPrivateKey=new ConfigReader().read().get("operatorPrivateKey").toString();
			String operatorpublicKey=new ConfigReader().read().get("operatorpublicKey").toString();
			 logger.warn("管理员私钥:"+operatorPrivateKey);
			 logger.warn("管理员公钥:"+operatorpublicKey);
			protobufBean=ProtobufUtils.RequestRecharge(operatorPrivateKey,operatorpublicKey,String.valueOf(uid),pubkey,rMoney);
			System.out.println("用户充值："+protobufBean);
		}
		return protobufBean;
	}

//	public static boolean sendPost(ProtobufBean protobufBean){
//		boolean flag = false;
//		String signdata = protobufBean.getSignature();
//		 String[] params = new String[1];
//         params[0] = signdata;
//         String jsonstr = JSON.toJSONString(new RequestAccountBean("2.0", "broadcast_tx_commit", null, params));
//         try {
//			String result =  httpRequest.sendPost(new ConfigReader().read().get("blockchainurl").toString(), jsonstr);
//			System.out.println(result);
//			 Result r1=JSON.parseObject(result, Result.class);
//			 if(!(r1.getError().isEmpty())){
//				 logger.warn("block chain error info:"+r1.getError() );
//				 flag = false;
//			 }else{
//				 Response response=JSON.parseObject((r1.getResult().get(1)).toString(),Response.class);
//				 if(response.getCode()==1){
//					 logger.warn("block chain error info:"+HexUtil.hexToString(response.getData()) );
//					 flag = false;
//				 }else{
//					 logger.warn("block chain success info:"+HexUtil.hexToString(response.getData()) );
//					 flag =  true;
//				 }
//			 }
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			flag = false;
//		}
//         return flag;
//	}
	
	public static boolean sendPostSign(String sign){
		boolean flag = false;
		String signdata =sign;
		 String[] params = new String[1];
		 logger.warn("%%%%%%%%%%%%%%send sign data dengqun %%%%%%%%%%%%%%:"+signdata);
         params[0] = signdata;
         String jsonstr = JSON.toJSONString(new RequestAccountBean("2.0", "broadcast_tx_commit", null, params));
         try {
			String result =  httpRequest.sendPost(new ConfigReader().read().get("blockchainurl").toString(), jsonstr);
			 logger.warn("#############recv data dengqun #############:"+result);
			 System.out.println(result);
			 Result r1=JSON.parseObject(result, Result.class);
			 if(!(r1.getError().isEmpty())){
				 logger.warn("block chain error info:"+r1.getError() );
				 flag = false;
			 }else{
				 Response response=JSON.parseObject((r1.getResult().get(1)).toString(),Response.class);
				 if(response.getCode()==1){
					 logger.warn("block chain error info:"+HexUtil.hexToString(response.getData()) );
					 flag = false;
				 }else{
					 logger.warn("block chain success info:"+HexUtil.hexToString(response.getData()) );
					 flag =  true;
				 }
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
         return flag;
	}
	
}
