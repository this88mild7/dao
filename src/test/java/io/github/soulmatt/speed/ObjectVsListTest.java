package io.github.soulmatt.speed;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import io.github.soulmatt.model.User;
import lombok.extern.slf4j.Slf4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
@Slf4j
public class ObjectVsListTest {

	@Autowired
	private io.github.soulmatt.dao.MongoDao mongoDao;
	
	@Autowired
	private io.github.soulmatt.dao.MybatisDao mybatisDao;
	
	@Before
	public void before(){
	}
	
	@After
	public void after(){
	}
	
	@Test
	public void testInsert() {
		
		int count = 500;
		
		long startTime1 = System.currentTimeMillis();

		List<User> list = new ArrayList<User>();
		for(int beginNum = 1; beginNum <= count; beginNum++){
			User user = new User("ic", "human" + beginNum, beginNum);
			list.add(user);
		}
		//mongoDao.save(list);
		mybatisDao.insertList(list);

		long finishTime1 = System.currentTimeMillis();

		log.info(count + "개 insert list : "+(finishTime1-startTime1)+ " ms");

		//----------------------------------------------------------------------------------------
		
		long startTime2 = System.currentTimeMillis();
		
		for(int beginNum = 1; beginNum <= count; beginNum++){
			User user = new User("ic", "human" + beginNum, beginNum);
			//mongoDao.save(user);
			mybatisDao.insert(user);
		}
		
		long finishTime2 = System.currentTimeMillis();
		
		log.info(count + "개 insert one by one : "+(finishTime2-startTime2)+ " ms");
	}
	
	@Test
	public void testInsertListVsList() {
		
		int count = 5000;
		
		List<User> list = new ArrayList<User>();
		for(int beginNum = 1; beginNum <= count; beginNum++){
			User user = new User("ic", "human" + beginNum, beginNum);
			list.add(user);
		}

		long startTime1 = System.currentTimeMillis();
		mybatisDao.insertList(list);
		long finishTime1 = System.currentTimeMillis();
		
		log.info(count + "개 insert list by mybatis: "+(finishTime1-startTime1)+ " ms");
		
		//----------------------------------------------------------------------------------------
		
		long startTime2 = System.currentTimeMillis();
		mongoDao.save(list);
		long finishTime2 = System.currentTimeMillis();
		
		log.info(count + "개 insert list by mongodb : "+(finishTime2-startTime2)+ " ms");
	}
	
	
	@Test
	public void testSelect() {
		long startTime1 = System.currentTimeMillis();
		log.info("mongoCount : ", mongoDao.list().size());
		long finishTime1 = System.currentTimeMillis();
		log.info("select by mongodb : "+(finishTime1-startTime1)+ " ms");

		//----------------------------------------------------------------------------------------
		
		long startTime2 = System.currentTimeMillis();
		log.info("mybatisCount : ", mybatisDao.list().size());
		long finishTime2 = System.currentTimeMillis();
		log.info("select by mybatis : "+(finishTime2-startTime2)+ " ms");
	}
	

}
