package com.ljh.persistence;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.ljh.domain.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO{
	
	@Inject
	private SqlSession sqlSession;

	// 매퍼.
	private static String namespace = "com.ljh.mappers.memberMapper";
	
	// 회원가입.
	@Override
	public void signup(MemberVO vo) throws Exception {
		sqlSession.insert(namespace + ".signup", vo);
	}

	@Override
	public MemberVO signin(MemberVO vo) throws Exception {
		return sqlSession.selectOne(namespace + ".signin", vo);
	}
}
