package io.github.soulmatt.mongodb;

import static org.junit.Assert.assertNotNull;

import io.github.soulmatt.model.User;

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
	private MongoOperations mongoOperation;
	
	@Test
	public void testBasicQuery() {
		
		BasicQuery query1 = new BasicQuery("{ age : { $lt : 40 }, name : 'cat' }");
		User userTest1 = mongoOperation.findOne(query1, User.class);
		 
		log.info("query1 - " + query1.toString());
		log.info("userTest1 - " + userTest1);
		
	}
	
	@Test
	public void testFindOne() {
		
		Query query2 = new Query();
		query2.addCriteria(Criteria.where("name").is("dog").and("age").is(40));
		 
		User userTest2 = mongoOperation.findOne(query2, User.class);
		log.info("query2 - " + query2.toString());
		log.info("userTest2 - " + userTest2);
		
	}
	
	@Test
	public void testFindWhereIn() {
		
		List<Integer> listOfAge = new ArrayList<Integer>();
		listOfAge.add(10);
		listOfAge.add(30);
		listOfAge.add(40);
		 
		Query query3 = new Query();
		query3.addCriteria(Criteria.where("age").in(listOfAge));
		 
		List<User> userTest3 = mongoOperation.find(query3, User.class);
		log.info("query3 - " + query3.toString());
		 
		for (User user : userTest3) {
			log.info("userTest3 - " + user);
		}
		
	}
	
	@Test
	public void testFindWhereGtLtAnd() {
		Query query4 = new Query();
		query4.addCriteria(Criteria.where("age").exists(true).andOperator(Criteria.where("age").gt(10), Criteria.where("age").lt(40)));
		 
		List<User> userTest4 = mongoOperation.find(query4, User.class);
		log.info("query4 - " + query4.toString());
		 
		for (User user : userTest4) {
			log.info("userTest4 - " + user);
		}
	}
	
	@Test
	public void testFindSort() {
		Query query5 = new Query();
		query5.addCriteria(Criteria.where("age").gte(30));
		query5.with(new Sort(Sort.Direction.DESC, "age"));
		 
		List<User> userTest5 = mongoOperation.find(query5, User.class);
		log.info("query5 - " + query5.toString());
		 
		for (User user : userTest5) {
			log.info("userTest5 - " + user);
		}
	}
	
	@Test
	public void testFindRegex() {
		Query query6 = new Query();
		query6.addCriteria(Criteria.where("name").regex("D.*G", "i"));
		 
		List<User> userTest6 = mongoOperation.find(query6, User.class);
		log.info("query6 - " + query6.toString());
		 
		for (User user : userTest6) {
			log.info("userTest6 - " + user);
		}
	}
	

}
