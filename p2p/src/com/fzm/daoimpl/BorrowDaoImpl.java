package com.fzm.daoimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fzm.daointerface.BorrowIDao;
import com.fzm.entity.BorrowVO;

@Service("borrows")
public class BorrowDaoImpl {

	@Autowired
	private BorrowIDao dao;
	/**实现了borrowIDao接口中的getBorrowInfo方法
	 * (根据产品的编号来获取对应的借款人的借款编号,然后查询出借款人在这次借款中的详细信息)
	 * @param fid 产品编号
	 * */
	public List<Map<String, Object>> findBorrow(String fid){
		
		return dao.getBorrowInfo(fid);
	}
	
	/**
	 * 实现了borrowIDao接口中的findBorrowInfo方法
	 * (根据借款编号来获取借款信息)
	 * @param fid 产品编号
	 * */
	public int findBorrowInfo(String fid){
		
		return dao.findBorrowInfo(fid);
	}
	
	/**
	 * 实现了borrowIDao接口中的findBorrowInfo方法
	 * (根据借款编号来获取借款编号)
	 * */
	public List<Integer> findBorrowBorIdList(String fid){
		
		return dao.findBorrowList(fid);
	}
	
	public BorrowVO findBorrowBorMoneyInfo(int borId){
		
		return dao.findBorrowBorMoney(borId);
	}
}
