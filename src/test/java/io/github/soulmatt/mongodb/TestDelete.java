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
public class TestDelete {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private MongoOperations mongoOperation;

	@Before
	public void before() {

		mongoOperation.dropCollection(User.class);
		// init
		List<User> listOfUser = new ArrayList<User>();

		User user1 = new User("1", "ant", 10);
		User user2 = new User("2", "bird", 20);
		User user3 = new User("3", "cat", 30);
		User user4 = new User("4", "dog", 40);
		User user5 = new User("5", "elephant", 50);
		User user6 = new User("6", "frog", 60);
		listOfUser.add(user1);
		listOfUser.add(user2);
		listOfUser.add(user3);
		listOfUser.add(user4);
		listOfUser.add(user5);
		listOfUser.add(user6);
		mongoOperation.insert(listOfUser, User.class);
	}

	@Test
	public void testRemovePart1() {

		Query query1 = new Query();
		query1.addCriteria(Criteria.where("name").exists(true).orOperator(Criteria.where("name").is("frog"), Criteria.where("name").is("dog")));
		mongoOperation.remove(query1, User.class);

	}

	@Test
	public void testRemovePart2() {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("name").is("bird"));
		User userTest2 = mongoOperation.findOne(query2, User.class);
		mongoOperation.remove(userTest2);

	}

	@Test
	public void testFindAndRemove() {
	
		// 검색 후 삭제
//		Query query3 = new Query();
//		query3.addCriteria(Criteria.where("name").is("ant"));
//		User userTest3 = mongoOperation.findAndRemove(query3, User.class);
//		log.info("Deleted document : " + userTest3);

		// cat 혹은 dog만 지워진다.(배치에선 사용하지 말것)
		Query query4 = new Query(); 
		query4.addCriteria(Criteria.where("name").exists(true).orOperator(Criteria.where("name").is("cat"),Criteria.where("name").is("elephant")));
		log.info("Deleted document : " + mongoOperation.findAndRemove(query4, User.class));
		

		List<User> allUsers = mongoOperation.findAll(User.class);
		log.info("All user list");
		for (User user : allUsers) {
			log.info(user.toString());
		}

	}
}
