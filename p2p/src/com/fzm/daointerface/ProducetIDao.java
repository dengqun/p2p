package com.fzm.daointerface;

import java.util.List;
import java.util.Map;

import com.fzm.entity.ProducetVO;



/**
 * FileName: financingIDao.java
 * @Description :对投资人投资详细信息的查新操作，执行投资操作,更新产品的状态       涉及到的表有  financing  u_financing
 * @author 创建人 王斌
 * @date 创建时间 2017-8-1下午5:04:18
 * @version v1.0
 *
 * Modification  History:
 * Date              Author           Version        
 * -------------------------------------------------
 * 2017-8-1           王斌                                         1.0         
 *
 * Why & What is modified:
 */
public interface ProducetIDao {

	 /**
     * 根据用户id（uid）来获取financing表中所有在发行中的理财产品的信息
     * */
	List<Map<String, Object>> getProducet(int uid,int startPageIndex,int rows);
	
	/**
	 *  根据用户的id（uid）来获取产品的id（pid），通过产品的id（pid）查询出用户已经投资的financing表中理财产品的信息
     * @param pid 产品id
	 * */
	List<Map<String, Object>> getProducetByPid(int uid,int startPageIndex,int rows);
	
	/**
	 * 根据产品的id（pid）来修改产品的状态
     * @param ProducetVO 根据表中某些字段进行查询
	 * */
	int updProducetByPid(ProducetVO vo);
	
	/**
	 * 将打包好的产品录入到financing表中
	 * @param ProducetVO 将表中所有字段添加进去
	 * */
	int insProducet(ProducetVO vo);
	
	/**
	 * 根据产品的id来修改融资金额
	 * @param fvo 当中包括了产品的id以及融资的金额
	 * */
	int findProducetUpdAmount(ProducetVO vo);
	
	/**
	 * 根据产品编号来查询并且返回
	 * @param pid 产品的编号
	 * */
	List<Map<String, Object>> findProducet(String pid);
	
	/**
	 * 根据产品编号来查询获取
	 * @param pid 产品的编号
	 * */
	ProducetVO gainProducet(String pid);
	
	/**
	 * 根据产品编号pid来查询投资金额和债券份额
	 * */
	List<Map<String, Object>> findProducetByPid(String pid);
	
	/**
	 * 根据用户id（uid）来获取financing表中所有在发行中的理财产品的信息总条数
	 * */
	int intCount(int uid);
	
	/**
	 * 根据用户id（uid）来获取financing表中所有没有投资的理财产品的信息总条数
	 * */
	int intCounts(int uid);
	
	
	/**
	 * 更新产品的投资进度
	 * */
	int findProducetUpdRaise(ProducetVO vo);
	
	
	/**
	 * 进行将到起息日的产品变为已失效   6
	 * */
	int updProducetState(String pid);
	
	/**
	 * 查询出所有的失效产品id
	 * */
	List<String> selectPid(String startDate);
	
	/**
	 * 获取当前日期的所有债券列表
	 * */
	List<String> selectPidInfo(String Date);
	
	/**
	 * 获取获取当前日期的所有到息日的产品
	 * */
	List<String> selectDuePid(String Date);
}
