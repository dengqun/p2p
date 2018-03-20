package com.fzm.daointerface;

import java.util.List;
import java.util.Map;

import com.fzm.entity.BorrowVO;

public interface BorrowIDao {

	/**
	 * 根据产品的编号来获取对应的借款人的借款编号
	 * 然后查询出借款人在这次借款中的详细信息
	 * @param fid 产品编号
	 * */
	List<Map<String, Object>> getBorrowInfo(String fid);
	
	/**
	 * 根据借款编号来获取借款信息
	 * @param borid 借款编号
	 */
	int findBorrowInfo(String fid);
	
	
	/**
	 * 根据产品编号获取所有的借款人的借款编号
	 * */
	List<Integer> findBorrowList(String fid);
	
	/**
	 * 获取借款人的借款金额
	 * */
	BorrowVO findBorrowBorMoney(int borId);
	
	/**
	 * 获取所有已失效的借条
	 * */
	List<Integer> selectUserBorId(String fid);
	
	/**
	 * 将已经失效的借条状态修改成已失效
	 * */
	int updBorrowUserBorState(int borId);
	
	/**
	 * 查询所有的已经失效的借款人id和借款金额
	 * */
	Map<String,Object> selectBorrowUserMsg(int borId);
	
	/**
	 * 更新用户的某张借条的还款金额
	 * */
	int updateRepayMoney(BorrowVO vo);
	
	/**
	 * 获取借条的用户id
	 * */
	int selUid(int borId);
	
	/**
	 * 获取某个产品下的借条Id
	 * */
	List<Integer> selBorId(String fid);
	
	/**
	 * 获取某个产品下的还款金额总和
	 * */
	int selCountBorMoney(String fid);
}
