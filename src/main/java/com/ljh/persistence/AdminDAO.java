package com.ljh.persistence;

import java.util.List;
import com.ljh.domain.CategoryVO;

public interface AdminDAO {
	// 카테고리.
	public List<CategoryVO> category() throws Exception;
}
