package io.github.soulmatt.mybatis;

import static org.junit.Assert.assertNotNull;

import io.github.soulmatt.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
@Slf4j
public class TestSelect {

	@Autowired
	private SqlSession sqlSession;
	
	@Test
	public void testSelectList() {
		
		try {
			List<User> listOfUser = sqlSession.selectList("userQuery.list");
			
			log.info(listOfUser.size() + "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
