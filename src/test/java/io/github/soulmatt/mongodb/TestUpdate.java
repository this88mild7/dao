package io.github.soulmatt.mongodb;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mongodb.xml" })
@Slf4j
public class TestUpdate {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MongoOperations mongoOperation;
	
	@Test
	public void testSaveOrUpdatePart1() {
		
		//init
		User user = new User();
		user.setIc("1001");
		user.setName("daewon1");
		user.setAge(31);
		user.setCdate(new Date());
		mongoOperation.save(user);
		
		//practice
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is("daewon1"));
	 
		User userTest1 = mongoOperation.findOne(query, User.class);
	 
		log.info("userTest1 - " + userTest1);
	 
		//modify and update with save()
		userTest1.setAge(99);
		mongoOperation.save(userTest1);
	 
		//get the updated object again
		User userTest1_1 = mongoOperation.findOne(query, User.class);
	 
		log.info("userTest1_1 - " + userTest1_1);
		
	}

	@Test
	public void testSaveOrUpdatePart2() {
		
		//init
		User user = new User();
		user.setIc("1002");
		user.setName("daewon2");
		user.setAge(30);
		user.setCdate(new Date());
		mongoOperation.save(user);
		
		//practice		
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is("daewon2"));
		query.fields().include("name");
 
		User userTest2 = mongoOperation.findOne(query, User.class);
		log.info("userTest2 - " + userTest2);
 
		userTest2.setAge(99);
 
		mongoOperation.save(userTest2);
 
		// ooppss, you just override everything, it caused ic=null and createdDate=null
 
		Query query1 = new Query();
		query1.addCriteria(Criteria.where("name").is("daewon2"));
 
		User userTest2_1 = mongoOperation.findOne(query1, User.class);
		log.info("userTest2_1 - " + userTest2_1);
		
	}
	
	@Test
	public void testUpdateFirst() {
		
		//init
		User user = new User();
		user.setIc("1003");
		user.setName("daewon3");
		user.setAge(30);
		user.setCdate(new Date());
		mongoOperation.save(user);
		
		//practice		
		
		//returns only 'name' field
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is("daewon3"));
		query.fields().include("name");
 
		User userTest3 = mongoOperation.findOne(query, User.class);
		log.info("userTest3 - " + userTest3);
 
		Update update = new Update();
		update.set("age", 100);
 
		mongoOperation.updateFirst(query, update, User.class);
 
		//returns everything
		Query query1 = new Query();
		query1.addCriteria(Criteria.where("name").is("daewon3"));
 
		User userTest3_1 = mongoOperation.findOne(query1, User.class);
		log.info("userTest3_1 - " + userTest3_1);
		
	}
	
	@Test
	public void testUpdateMulti() {
		
		//init
		User user1 = new User();
		user1.setIc("1004");
		user1.setName("daewon4");
		user1.setAge(31);
		user1.setCdate(new Date());
		mongoOperation.save(user1);

		User user2 = new User();
		user2.setIc("1005");
		user2.setName("daewon5");
		user2.setAge(31);
		user2.setCdate(new Date());
		mongoOperation.save(user2);
		
		//practice		
		
		//show the use of $or operator
		Query query = new Query();
		query.addCriteria(Criteria
				.where("name").exists(true)
				.orOperator(Criteria.where("name").is("daewon4"),
						Criteria.where("name").is("daewon5")));
		Update update = new Update();
 
		//update age to 11
		update.set("age", 11);
 
		//remove the createdDate field
		update.unset("createdDate");
 
		// if use updateFirst, it will update 1004 only.
		//mongoOperation.updateFirst(query, update, User.class);
 
		// update all matched, both 1004 and 1005
		mongoOperation.updateMulti(query, update, User.class);
 
		log.info(query.toString());
 
		List<User> usersTest4 = mongoOperation.find(query, User.class);
 
		for (User userTest4 : usersTest4) {
			log.info("userTest4 - " + userTest4);
		}
		
	}
	
	@Test
	public void testUptsert() {
		
		//init
		
		
		//practice		
		
		//search a document that doesn't exist
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is("daewon77"));
 
		Update update = new Update();
		update.set("age", 77);
 
		mongoOperation.upsert(query, update, User.class);
 
		User userTest5 = mongoOperation.findOne(query, User.class);
		log.info("userTest5 - " + userTest5);
		
	}
	
	
	@Test
	public void testFindAndModify() {
		
		//init
		User user = new User();
		user.setIc("1006");
		user.setName("daewon6");
		user.setAge(30);
		user.setCdate(new Date());
		mongoOperation.save(user);
		
		
		//practice		
		Query query6 = new Query();
		query6.addCriteria(Criteria.where("name").is("daewon6"));
 
		Update update6 = new Update();
		update6.set("age", 101);
		update6.set("ic", 1111);
 
		//FindAndModifyOptions().returnNew(true) = newly updated document
		//FindAndModifyOptions().returnNew(false) = old document (not update yet)
		User userTest6 = mongoOperation.findAndModify(
				query6, update6, 
				new FindAndModifyOptions().returnNew(false), User.class);
		log.info("userTest6 - " + userTest6);
		
	}
	
}
