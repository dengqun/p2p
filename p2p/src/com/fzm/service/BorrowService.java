package com.fzm.service;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fzm.daoimpl.BorrowDaoImpl;
import com.fzm.daoimpl.ProducetDaoImpl;

@Service("borrow")
public class BorrowService {

	@Resource(name="borrows")
	private BorrowDaoImpl bserv;
	@Resource(name="producet")
	private ProducetDaoImpl fserv;
	
	/**
	 * 点击未投资的产品打开的产品详情以及借款人的详细借款情况
	 * @param fid 产品的编号
	 * @throws IOException 
	 * */
	public Map<String,Object> select(String fid){
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<Map<String,Object>> producetEntity = fserv.getProducet(fid);
		List<Map<String,Object>> bList = bserv.findBorrow(fid);
		map.put("code", "200");
		map.put("StateMsg","查询借款包详情成功");
		map.put("StateCode", true);
		map.put("financingResult", producetEntity);
		map.put("borrowResult", bList);
		return map;
			
	}
}
