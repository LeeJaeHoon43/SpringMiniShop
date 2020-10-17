package com.ljh.service;

import java.util.List;
import com.ljh.domain.CategoryVO;

public interface AdminService {
	// 카테고리.
		public List<CategoryVO> category() throws Exception;
}
