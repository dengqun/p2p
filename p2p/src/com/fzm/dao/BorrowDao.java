
package com.fzm.dao;

import java.util.List;

import java.util.Map;



import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;



import com.fzm.entity.AttachmentVO;
import com.fzm.entity.Borrow;
import com.fzm.entity.Financing;
import com.fzm.entity.User;

/**
 * 
 * @Description : 借款表相关方法调用
 * @author sdy
 * @date 2017-08-01
 * 
 */
@Repository
public class BorrowDao {
			@Autowired
			private SqlSessionTemplate dao;
			/**
			 *  entity 用户
			 *  该用户的借款列表
			 */
			public List<Borrow> getTotalBor(User entity){
				return dao.selectList("BorMapper.getTotalBor",entity);
			}
			
			/**
			 * 提交申請
			 * 添加借款
			 * @param entity	借款对象
			 * @return
			 */
			public int applyFor(Borrow entity) {
				
				 return dao.insert("BorMapper.applyFor",entity);
				
			}
			/**
			 * 
			 * 修改借款状态
			 * @param	entity 
			 * @return 
			 */
			public int updateBorState(Borrow entity) {
			return	dao.update("BorMapper.updateBorState",entity);
				
			}
			/**
			 * 通过borId查询借款信息
			 * @param entity
			 * @return
			 */
			public Borrow findById(Borrow entity) {
				return dao.selectOne("BorMapper.findById",entity);
				
			}
			/**
			 * 通过状态
			 * @param entity
			 * @return
			 */
			public List<Borrow> findByState(Borrow entity) {
				
				return dao.selectList("BorMapper.findByState",entity);
			}
			/**
			 * 通过产品包查询借款信息
			 * @param entity
			 * @return
			 */
			public List<Borrow> findByfid(Financing entity) {
				
				return dao.selectList("BorMapper.findByfid",entity);
			}
			/**
			 * 更新借款信息
			 * @param entity
			 * @return
			 */
			public int updateBor(Borrow entity) {
			
				return dao.update("BorMapper.updateBor",entity);
			}
			/**
			 * 通过借款编号查询用户Id
			 * @param entity
			 * @return
			 */
			public Borrow findUidByBorId(Borrow entity) {
				return dao.selectOne("BorMapper.findUidByBorId",entity);
				
			}
			/**
			 * 打包时添加关联表中数据
			 * @param map
			 * @return
			 */
			public int CreateB_Financing(Map<String, Object> map) {
				return dao.insert("BorMapper.CreateB_Financing",map);
			}
			/**
			 * 更新状态
			 * @param bor
			 * @return
			 */
			public int updateStartDate(Borrow bor) {
			
				return dao.update("BorMapper.updateStartDate",bor);
			}
			
			
			
			
}
