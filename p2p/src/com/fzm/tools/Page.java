package com.fzm.tools;

import java.util.List;

public class Page<T> {

	// 传递的参数或配置的项
	private int currentPage; // 当前页
	private int pre;
	private int next;
	private int pageSize; // 每页显示多少条
	// 查询数据库
	private int recordCount; // 总记录数
	private List<T> recordList; // 本页的数据列表

	// 计算
	private int pageCount; // 总页数
	private int beginPageIndex; // 页码列表的开始索引
	private int endPageIndex; // 页码列表的结束索引
//	private boolean isPrevious; // 是否存在上一页
//	private boolean isNext; // 是否存在下一页
	
	/**
	 * 只需要接受前4个参数的值，会自动的计算出后3个属性的值。
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param recordCount
	 * @param recordList
	 */
	public Page(int currentPage, int pageSize, int recordCount,List<T> recordList) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.recordCount = recordCount;
		this.recordList = recordList;
		this.pre = this.currentPage - 1;
		this.next = this.currentPage + 1;
		// 计算pageCount
		pageCount = (recordCount + pageSize - 1) / pageSize;
		
		// 计算是否存在上一页和下一页
		
		
		// 计算beginPageIndex和endPageIndex
		// 当页码数量不大于10个时，显示所有页码。
		if (pageCount <= 10) {
			beginPageIndex = 1;
			endPageIndex = pageCount;
		}
		// 当页码数量大于10个时，显示当前页附近的共10个页码。
		else {
			// 一般情况下显示前4个+当前页+后5个（共10个）
			beginPageIndex = currentPage - 4;
			endPageIndex = currentPage + 5;

			// 当前面不足4个页码时，显示前10个页码
			if (beginPageIndex < 1) {
				beginPageIndex = 1;
				endPageIndex = 10;
			}
			// 当后面不足5个页码时，显示后10个页码
			else if (endPageIndex > pageCount) {
				endPageIndex = pageCount;
				beginPageIndex = pageCount - 10 + 1; // 显示时会包含两边的边界，所以要减9.
			}
		}
	}

	
	public Page(int currentPage,int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.beginPageIndex= (currentPage - 1) * pageSize;
	}
	
	public Page() {}




	public int getPre() {
		return pre;
	}

	public void setPre(int pre) {
		this.pre = pre;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public List<T> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<T> recordList) {
		this.recordList = recordList;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}




//	public boolean isPrevious() {
//		return isPrevious;
//	}
//
//	public void setPrevious(boolean isPrevious) {
//		this.isPrevious = isPrevious;
//	}

//	public boolean isNext() {
//		return isNext;
//	}
//
//	public void setNext(boolean isNext) {
//		this.isNext = isNext;
//	}

}
