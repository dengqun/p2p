package com.fzm.task;

import java.text.SimpleDateFormat;




import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import com.fzm.daoimpl.ProducetDaoImpl;
import com.fzm.daoimpl.InvestDaoImpl;
import com.fzm.daoimpl.UserDaoImpl;
import com.fzm.daointerface.BorrowIDao;
import com.fzm.daointerface.ProducetIDao;
import com.fzm.daointerface.InvestIDao;
import com.fzm.daointerface.UserIDao;
import com.fzm.entity.ProducetVO;
import com.fzm.entity.User;
import com.fzm.entity.InvestVO;

public class TaskInit{

	@Autowired
	private ProducetIDao fdao;
	@Autowired
	private InvestIDao ufdao;
	@Autowired
	private UserIDao udao;
	@Autowired
	private BorrowIDao bdao;
	@Resource(name="user")
	private UserDaoImpl userv;
	@Resource(name="invest")
	private InvestDaoImpl ufser;
	@Resource(name="producet")
	private ProducetDaoImpl fserv;
	
	
	/**
	 * 1.产品失效     每天0点对达到起息日但没有投满的产品进行状态更改   更改为已失效          执行方法：  updateFinancingState()   
	 * 2.投资人收款       对到达到息日24点的产品进行对投资人进行打钱          执行方法：  updateBorrowMoney()方法
	 * 3.逾期金额重新计算       对已经所有已经逾期的产品进行还款金额的重新计算        执行方法：  overdue()方法
	 * 4.分期还款    每个月的最后还款日的24点对投资人进行打钱           执行方法： periodization()方法 
	 * 5.分期逾期    对分期还款逾期的人进行还款金额重新计算         执行方法： overdueInstalment()方法
	 * */
	public void Task(){
		System.out.println("----------------进入定时器-------------");
		for(int code = 1;code<=10;code++){
			System.out.println(code);
			if(code==1){
				this.updateFinancingState();
			}
			if(code==2){
				this.updateBorrowMoney();
			}
			if(code==3){
				this.overdue();
			}
			if(code==4){
				this.periodization();
			}
			if(code==5){
				this.overdueInstalment();
			}
		}
	}
	
	/**
	 * 每天0点对达到起息日但没有投满的产品进行状态更改   更改为已失效
	 *  1.获取所有的是当前日期是到期日且没有投满的产品的产品id
	 *  2.将查询出来的产品的状态变更为已失效
	 *  3.根据产品id获取所有的投资人id
	 *    将投资人的投资份额打到账户余额，投资人的债券资产减去投资份额同时修改用户的投资状态
	 *  4.根据产品id获取所有的借条id，根据借条id查询出对应的借款人id，借款份额，还款份额
	 *  5.依据借款人id对借款人的资产进行修改
	 *        详细修改:(1):借款人已借额度  - 借款份额
	 *        		(2):借款人可借额度  + 借款份额
	 *  		    (3):借款人共计还款  - 还款份额
	 * */
	public void updateFinancingState(){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date =df.format(new Date());
		
		List<String> fList = fdao.selectPid(date);		//---1 Start And End
		
		User vo = new User();
		
		for(int i=0;i<fList.size();i++){
			 
			String producetid = fList.get(i);	//---2 Start
			fdao.updProducetState(producetid);//---2 End
			InvestVO ufvo = new InvestVO();
			ufvo.setInvest_stateNo(6);	//---3 Start
			ufvo.setInvest_producet_id(producetid);	
			ufdao.updInvestStateNo(ufvo);
			List<Integer> uflist = ufdao.selectUserUid(producetid);
			for(int a=0;i<uflist.size();i++){
				int uid = uflist.get(a);
				Map<String, Object> maps = ufdao.selectUserMsg(uid);
				double investMoney = Double.parseDouble(maps.get("investMoney").toString());
				vo = udao.gainUserInfo(uid);
				vo.setUid(uid);
				vo.setBalance(vo.getBalance() + investMoney);
				vo.setBondHoldings(vo.getBondHoldings() - investMoney);
				vo.setTotalAssets(vo.getBalance() + vo.getBondHoldings());
				udao.updMoney(vo);
			}
			//---3 End 
		}
		for(int j=0;j<fList.size();j++){//---4 Start
			String fids = fList.get(j);
			List <Integer> blist = bdao.selectUserBorId(fids);
			for(int k=0;k<blist.size();k++){
				List<Integer> bflist = bdao.selectUserBorId(fids);//---4 End
				for(int l=0;l<bflist.size();l++){
					int borId = bflist.get(l);//---5 Start
					bdao.updBorrowUserBorState(borId);
					Map<String, Object> mapl = bdao.selectBorrowUserMsg(borId);
					int uid = Integer.parseInt(mapl.get("uid").toString());
					double borMoney = Double.parseDouble(mapl.get("borMoney").toString());
					double repayMoney = Double.parseDouble(mapl.get("repayMoney").toString());
					vo =udao.gainUserInfo(uid);
					vo.setUid(uid);
					vo.setBorTotal(vo.getBorTotal() - borMoney);   //--5.1
					vo.setAvailableCredit(vo.getAvailableCredit() + borMoney);//--5.2
					vo.setReTotal(vo.getReTotal() - repayMoney);	 //--5.3
					udao.updBorrow(vo);
				}//---5  End
			}
		}
	}
	
	/**
	 * 每天0点对前一天为到息日的产品进行还款处理
	 *  1.获取当前日期是到期日的产品
	 *	2.判断每一个产品里面是否有人没有还款
	 *	3.全部还款完毕走收款
	 *	4.有人没有还款走逾期处理
	 * */
	public void updateBorrowMoney(){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date =df.format(new Date());
		InvestVO ivo = new InvestVO();
		User user = new User();
		List<String> flist = fdao.selectDuePid(date);	// ---1 Start And End
		for(int i=0;i<flist.size();i++){
			ProducetVO producet = fdao.gainProducet(flist.get(i));	//---2 Start
			int count = bdao.selCountBorMoney(producet.getPid());	//---2 End
			if(count==0){
				//收款
				// 1.根据产品的id获取所有的投资人id
				// 2.对投资人进行打钱,同时修改投资人的账户余额，债券金额，总资产，投资状态
				// 3.修改产品的投资状态
				
				List<Integer> usid = ufser.select(producet.getPid());	//---收款1 Start And End
				for(int j:usid){
					ivo.setInvest_producet_id(producet.getPid());//---收款2  Start
					ivo.setInvest_uid(j);
					ivo =  ufser.selectUF(ivo);
					ivo.setInvest_stateNo(5);
					ufser.updUfsid(ivo);
					user = userv.findUserList(ivo.getInvest_uid());
					user.setUid(ivo.getInvest_uid());
					user.setBalance(user.getBalance() + ivo.getInvest_eRevenue());
					user.setBondHoldings(user.getBondHoldings() - ivo.getInvestMoney());
					user.setTotalAssets(user.getBalance() + user.getBondHoldings());
					userv.updateMoney(user);;//---收款2  End
				}
				producet.setProducet_stateNo(5);	//---收款3 Start
				fserv.updateProducetByPid(producet);	//---收款3 End
			}else{
//				//逾期处理
//				// 1.将没有还款的借款人状态修改为已逾期
//				// 2.将已经还款金额打给投资人并将投资人状态修改成已逾期
//				// 3.产品的状态变更为已逾期
//				//---逾期处理1 Start And End  (未做)
//				List<Integer> usid = ufser.select(producet.getPid());	
//				for(int j:usid){
//					ivo.setFid(producet.getPid());//---逾期处理2  Start
//					ivo.setUid(j);
//					ivo =  ufser.selectUF(ivo);
//					ivo.setUfsid(逾期编号);
//					ufser.updUfsid(ivo);
//					user = userv.findUserList(ivo.getUid());
//					user.setUid(ivo.getUid());
//					double eRevence = Math.floor(producet.getInvest_eRevenue()*
//							ivo.getInvestMoney()/producet.getProducet_money()*100)/100;//用户的收益
//					user.setBalance(user.getBalance()+eRevence);
//					user.setBondHoldings(user.getBondHoldings()-ivo.getInvestMoney());
//					user.setTotalAssets(user.getBalance()+user.getBondHoldings());
//					userv.updateMoney(user);;//---逾期处理2  End
//				}
//				producet.setStateNo(逾期编号);	//---逾期处理3 Start
//				fserv.updateProducetByPid(producet);	//---逾期处理3 End
			} 
		}
	}
	
	/**
	 * 分期还款
	 * 每个月的最后还款日的24点对投资人进行打钱
	 * */
	public void periodization(){
		
	}
	
	/**
	 * 分期逾期
	 * 对分期还款逾期的人进行还款金额重新计算
	 * */
	public void overdueInstalment(){
		
	}
	
	/**
	 * 普通逾期
	 * 对正常还款逾期的人进行还款金额重新计算
	 * */
	public void overdue(){
		
	}
}
