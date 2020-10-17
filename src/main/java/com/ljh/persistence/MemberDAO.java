package com.ljh.persistence;

import com.ljh.domain.MemberVO;

public interface MemberDAO {
	// 회원가입.
	public void signup(MemberVO vo) throws Exception;
	
	// 로그인.
	public MemberVO signin(MemberVO vo) throws Exception;
}
