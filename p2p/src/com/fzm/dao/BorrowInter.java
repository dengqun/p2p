package com.fzm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fzm.entity.Borrow;
import com.fzm.entity.User;

@Repository
public interface BorrowInter {
	public List<Borrow> queryBorrowList(User entity);
	public int queryAllBorrowList(User entity);
	
}
