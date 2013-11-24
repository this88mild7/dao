package io.github.soulmatt.speed;

import static org.junit.Assert.*;
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
@ContextConfiguration(locations = { "classpath:mongodb.xml", "classpath:mybatis-context.xml" })
@Slf4j
public class SpeedTest {

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
	public void test500() {
		
		int count = 500;
		
		long startTime1 = System.currentTimeMillis();

		for(int beginNum = 1; beginNum <= count; beginNum++){
			User user = new User("ic", "human" + beginNum, beginNum);
			mongoDao.save(user);
		}

		long finishTime1 = System.currentTimeMillis();

		log.info(count + "개 insert by mongodb : "+(finishTime1-startTime1)+ " ms");

		//----------------------------------------------------------------------------------------
		
		long startTime2 = System.currentTimeMillis();
		
		for(int beginNum = 1; beginNum <= count; beginNum++){
			User user = new User("ic", "human" + beginNum, beginNum);
			mybatisDao.insert(user);
		}
		
		long finishTime2 = System.currentTimeMillis();
		
		log.info(count + "개 insert by mybatis : "+(finishTime2-startTime2)+ " ms");
	}
	
	@Test
	public void test1000() {
		
		int count = 1000;
		
		long startTime1 = System.currentTimeMillis();
		
		for(int beginNum = 1; beginNum <= count; beginNum++){
			User user = new User("ic", "human" + beginNum, beginNum);
			mongoDao.save(user);
		}
		
		long finishTime1 = System.currentTimeMillis();
		
		log.info(count + "개 insert by mongodb : "+(finishTime1-startTime1)+ " ms");
		
		//----------------------------------------------------------------------------------------
		
		long startTime2 = System.currentTimeMillis();
		
		for(int beginNum = 1; beginNum <= count; beginNum++){
			User user = new User("ic", "human" + beginNum, beginNum);
			mybatisDao.insert(user);
		}
		
		long finishTime2 = System.currentTimeMillis();
		
		log.info(count + "개 insert by mybatis : "+(finishTime2-startTime2)+ " ms");
	}
	
	@Test
	public void test2000() {
		
		int count = 2000;
		
		long startTime1 = System.currentTimeMillis();
		
		for(int beginNum = 1; beginNum <= count; beginNum++){
			User user = new User("ic", "human" + beginNum, beginNum);
			mongoDao.save(user);
		}
		
		long finishTime1 = System.currentTimeMillis();
		
		log.info(count + "개 insert by mongodb : "+(finishTime1-startTime1)+ " ms");
		
		//----------------------------------------------------------------------------------------
		
		long startTime2 = System.currentTimeMillis();
		
		for(int beginNum = 1; beginNum <= count; beginNum++){
			User user = new User("ic", "human" + beginNum, beginNum);
			mybatisDao.insert(user);
		}
		
		long finishTime2 = System.currentTimeMillis();
		
		log.info(count + "개 insert by mybatis : "+(finishTime2-startTime2)+ " ms");
	}
	
	@Test
	public void testSelect() {
		long startTime1 = System.currentTimeMillis();
		log.info("mongoCount : ", mongoDao.list());
		long finishTime1 = System.currentTimeMillis();
		log.info("select by mongodb : "+(finishTime1-startTime1)+ " ms");

		//----------------------------------------------------------------------------------------
		
		long startTime2 = System.currentTimeMillis();
		log.info("mybatisCount : ", mybatisDao.list());
		long finishTime2 = System.currentTimeMillis();
		log.info("select by mybatis : "+(finishTime2-startTime2)+ " ms");
	}
	

}
