package com.fzm.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fzm.daointerface.InvestIDao;
import com.fzm.entity.InvestVO;

/**
 * FileName: investService.java
 * @Description :TODO
 * @author 创建人 王斌
 * @date 创建时间 2017-8-3下午6:42:34
 * @version v1.0
 *
 * Modification  History:
 * Date              Author           Version        
 * -------------------------------------------------
 * 2017-8-3           王斌                                          @version          
 *
 * Why & What is modified:
 */

@Component("invest")
public class InvestDaoImpl {

	@Autowired
	private InvestIDao ufdao;
	/**
	 * 实现了investIDao接口中的insertInvest方法
	 * 插入用户与产品相关联的信息
	 * */
	public int insert(InvestVO vo){
		
		return ufdao.insertInvest(vo);
	}
	
	/**
	 * 实现了investIDao接口中的updObtain方法
	 * 更新用户在某个理财产品中的回笼资金（obtain）
	 * */
	public int updateObtain(InvestVO vo){
		
		return ufdao.updObtain(vo);
	}
	
	/**
	 * 实现了investIDao接口中的getInvestInfo方法
	 * 根据产品编号获取所有的投资人的用户id(uid)
	 * */
	public List<Integer> select(String fid){
	
		return ufdao.getInvestInfo(fid);
	}
	

	/**
	 * 实现了investIDao接口中的updInvestStateNo方法
	 * 根据产品编号修改投资人状态
	 * */
	public int updUfsid(InvestVO vo){
		
		return ufdao.updInvestStateNo(vo);
	}
	/**
	 * 实现了investIDao接口中的selectInvest方法
	 * 根据用户的uid和产品的fid来获取p2p_invest表中的信息
	 * */
	public InvestVO selectUF(InvestVO vo){
		
		return ufdao.selectInvest(vo);
	}
	
	/**
	 * 实现了investIDao接口中的findProducetScale方法
	 * 根据产品id获取所有已经投资人的投资占比总和
	 * */
	public double selectScale(InvestVO vo){
	
		return ufdao.findProducetScale(vo);
	}
	
	/**
	 * 实现了investIDao接口中的updScale方法
	 * 根据用户id和产品id更新最后一个投资人的投资占比
	 * */
	public int updateSacle(InvestVO vo){
		
		return ufdao.updScale(vo);
	}
	
	/**
	 * 实现了investIDao接口中的getProducetCountById方法
	 * 根据产品编号fid和用户id来查询用户是否投资
	 * */
	public int getCountById(InvestVO vo){
		
		return ufdao.getProducetCountById(vo);
	}
}
