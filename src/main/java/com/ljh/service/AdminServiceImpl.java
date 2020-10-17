package com.ljh.service;

import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import com.ljh.domain.CategoryVO;
import com.ljh.persistence.AdminDAO;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Inject
	private AdminDAO dao;

	@Override
	public List<CategoryVO> category() throws Exception {
		return dao.category();
	}
}
