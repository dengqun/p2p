package com.fzm.dao;





import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;



import com.fzm.entity.Borrow;
import com.fzm.entity.Financing;
/**
 * 
 * @Description : 产品表相关方法调用
 * @author sdy
 * @date 2017-08-01
 * 
 */
@Component
public class FinancingDao {
			@Autowired
			private SqlSessionTemplate dao;
			/**
			 * 通過借款Id查詢產品編號
			 * @param entity
			 * @return
			 */
			public String findFinByBor(Borrow entity) {
			return	dao.selectOne("FinancingMapper.findFinByBor",entity);
			}
			
			/**
			 * 通过产品Id查询产品信息
			 * @param entity
			 * @return
			 */
			public Financing findById(Financing entity) {
			return	dao.selectOne("FinancingMapper.findById",entity);
				
			}
			/**
			 * @param fid
			 */
			public int updateErevenue(Financing entity) {
				return	dao.update("FinancingMapper.updateErevenue",entity);
				
			}
			
			
			
			
			
}
