package com.fzm.daoimpl;

import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fzm.daointerface.ProducetIDao;
import com.fzm.entity.ProducetVO;



/**
 * FileName: financingService.java
 * @Description :financingIDao接口的具体实现                  
 * 				 financing接口实例化成类，将fdao中的方法反射打具体类中
 * @author 创建人 王斌
 * @date 创建时间 2017-8-1下午7:45:01
 * @version v1.0
 * 
 * Modification  History:
 * Date              Author           Version        
 * -------------------------------------------------
 * 2017-8-1           王斌                                          @version          
 *
 * Why & What is modified:
 */
@Component("producet")
public class ProducetDaoImpl {

	@Autowired
	private ProducetIDao fdao;
	/**
	 * 实现了fdao接口中的getProducet方法 
	 * */
	public List<Map<String, Object>> gainProducet(int uid,int startPageIndex,int rows){
		
		return fdao.getProducet(uid,startPageIndex,rows);
	}
	  
	/**
	 * 实现了fdao接口中的getProducetByPid方法 
	 * (根据用户的id（uid）来获取产品的id（fid），通过产品的id（fid）查询出用户已经投资的financing表中理财产品的信息)
	 * @param fid  fdao接口中getProducetByPid的参数
	 * */
	public List<Map<String, Object>> gainProducetByPid(int uid,int startPageIndex,int rows){
		
		return fdao.getProducetByPid(uid,startPageIndex,rows);
	}
	
	
	/**
	 * 实现了fdao接口中的updProducetByPid方法 (根据产品的id（fid）来修改产品的状态)
	 * @param vo  fdao接口中updProducetByPid的参数列表
	 * */
	public int updateProducetByPid(ProducetVO vo){
		
		return fdao.updProducetByPid(vo);
	}
	
	/**
	 * 实现了fdao接口中的insProducet方法 (将打包好的产品录入到financing表中)
	 * @param vo  fdao接口中insertProducet的参数列表
	 * */
	public int insertProducet(ProducetVO vo){
		
		return fdao.insProducet(vo);
	}
	/**
	* 实现了fdao接口中的findProducetUpdAmount方法(根据产品的id来修改融资金额)
	* 
	*/
	public int UpdAmount(ProducetVO vo){
		
		return fdao.findProducetUpdAmount(vo);
	}
	
	/**
	 * 实现了fdao接口中的findProducet方法 (根据产品编号来查询)
	 * */
	public List<Map<String, Object>> getProducet(String fid){
		
		return fdao.findProducet(fid);
	}
	
	/**
	 * 实现了fdao接口中的findProducetBuPid方法(根据产品编号来查询投资金额和债券份额)
	 * */
	public List<Map<String, Object>> getPidInfo(String fid){
		
		return fdao.findProducetByPid(fid);
	}
	
	/**
	 * 实现了fdao接口中的gainProducet方法(根据产品编号来查询产品详细信息)
	 * */
	public ProducetVO findProducetList(String fid){
		
		return fdao.gainProducet(fid);
	}
	
	/**
	 * 根据用户id（uid）来获取financing表中所有在发行中的理财产品的信息总条数
	 * */
	public int getCount(int uid){
		
		return fdao.intCount(uid);
	}
	
	/**
	 * 根据用户id（uid）来获取financing表中所有没有投资的理财产品的信息总条数
	 * */
	public int getCounts(int uid){
		
		return fdao.intCounts(uid);
	}
	
	/**
	 * 修改产品的投资进度
	 * */
	public int  updRaise(ProducetVO vo){
		
		return fdao.findProducetUpdRaise(vo);
	}
}
