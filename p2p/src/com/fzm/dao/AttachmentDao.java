package com.fzm.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;


import com.fzm.entity.AttachmentVO;
import com.fzm.entity.User;
/**
 * 
 * @Description : 附件相关
 * @author dengqun
 * @date 2017-08-14
 * 
 */
@Repository
public interface AttachmentDao {
		
		public int insertAttachment(AttachmentVO attachment);
		public int updateAttachment(AttachmentVO attachment);
		
		public int insertAttachment1(AttachmentVO attachment);
		public int updateAttachment1(AttachmentVO attachment);
		
		List<Map<String, Object>> selectAttachment(String userid);
}
