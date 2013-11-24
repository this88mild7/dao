package io.github.soulmatt.dao;

import java.util.List;

import io.github.soulmatt.model.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisDao {

	@Autowired
	private SqlSession sqlSession;
	
	public void insert(User user){
		sqlSession.insert("userQuery.insert", user);
	}
	
	public void insertList(List<User> listOfUser){
		sqlSession.insert("userQuery.insertList", listOfUser);
	}
	
	public List<User> list(){
		return sqlSession.selectList("userQuery.list");
	}
	
}
