package com.fzm.daointerface;

import java.util.List;

import java.util.Map;

import com.fzm.entity.InvestVO;

/**
 * FileName: ufinancingIDao.java
 * @Description :TODO
 * @author 创建人 王斌
 * @date 创建时间 2017-8-3下午6:35:16
 * @version v1.0
 *
 * Modification  History:
 * Date              Author           Version        
 * -------------------------------------------------
 * 2017-8-3           王斌                                          @version          
 *
 * Why & What is modified:
 */
public interface InvestIDao {


	/**
	 * 插入用户与产品相关联的信息
	 * */
	int insertInvest(InvestVO vo);
	
	/**
	 * 更新用户在某个理财产品中的回笼资金（advanced）
	 * */
	int updObtain(InvestVO vo);
	
	/**
	 * 根据产品编号获取所有的投资人的用户id(uid)
	 * @param fid 产品编号
	 * */
	List<Integer> getInvestInfo(String fid);
	
	/**
	 * 根据产品编号修改投资人状态
	 * */
	int updInvestStateNo(InvestVO ufvo);
	
	/**
	 * 根据用户id和产品id获取信息
	 * */
	InvestVO selectInvest(InvestVO vo);
	
	/**
	 * 根据产品id获取所有已经投资人的投资总和
	 * */
	double findProducetScale (InvestVO vo);
	
	/**
	 * 根据用户id和产品id更新最后一个投资人的投资占比
	 * */
	int updScale(InvestVO vo);
	
	/**
	 * 根据产品编号fid和用户id来查询用户是否投资
	 * */
	int getProducetCountById(InvestVO ufvo);
	
	/**
	 * 查询出已失效的用户id和投资金额
	 * */
	Map<String,Object> selectUserMsg(int uid);
	
	/**
	 * 获取所有已失效的产品的投资人Id
	 * */
	List<Integer> selectUserUid(String fid);
	
	/**
	 * 根据产品编号fid和用户id来更新区块链hash
	 * */
	int updHash(InvestVO ufvo);
}
