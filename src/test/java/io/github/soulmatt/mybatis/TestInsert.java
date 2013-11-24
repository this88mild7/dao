package io.github.soulmatt.mybatis;

import java.util.ArrayList;
import java.util.List;

import io.github.soulmatt.dao.MybatisDao;
import io.github.soulmatt.model.User;
import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
@Slf4j
public class TestInsert {

	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private MybatisDao mybatisDao;
	
	@Test
	public void testInsert() {
		
		try {
			User user = new User("a","b",1);
			sqlSession.insert("userQuery.insert", user);
			
			log.info(user.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testInsertList() {
		
		try {
			List<User> list = new ArrayList<User>();
			User user = new User("a","b",1);
			User user2 = new User("a","b",1);
			list.add(user);
			list.add(user2);
			mybatisDao.insertList(list);
			
			log.info(user.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
