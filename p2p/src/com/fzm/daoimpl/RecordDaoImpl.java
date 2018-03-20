package com.fzm.daoimpl;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fzm.daointerface.RecordIDao;

/**
 * FileName: recordService.java
 * @Description :TODO
 * @author 创建人 王斌
 * @date 创建时间 2017-8-10下午7:35:38
 * @version v1.0
 *
 * Modification  History:
 * Date              Author           Version        
 * -------------------------------------------------
 * 2017-8-10           王斌                                          @version          
 *
 * Why & What is modified:
 */
@Component("record")
public class RecordDaoImpl{

	@Autowired
	private RecordIDao rdao;
	
	/**
	 * 查询充值记录
	 * */
	public List<Map<String, Object>> selectRecord(int uid,int startPageIndex,int rows){
		
		return rdao.selRecord(uid,startPageIndex,rows);
	}
	
	/**
	 * 查询提款记录
	 * */
	public List<Map<String, Object>> selectURecord(int uid,int startPageIndex,int rows){
		
		return rdao.selURecord(uid,startPageIndex,rows);
	}
	
	/**
	 * 充值记录总条数
	 * */
	public int getCount(int uid){
		
		return rdao.intCount(uid);
	}
	
	/**
	 * 提现记录总条数
	 * */
	public int getCounts(int uid){
		
		return rdao.intCounts(uid);
	}
}
