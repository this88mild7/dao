package io.github.soulmatt.mongodb;

import static org.junit.Assert.assertNotNull;

import io.github.soulmatt.model.Lucky;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mongodb.xml" })
public class TestInsert {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Before
	public void before(){
		log.info("host {}", mongoTemplate.getDb().getMongo().getServerAddressList().toString());
		log.info("db {}", mongoTemplate.getDb().toString());
	}
	
	@Test
	public void testSave() {
		
		//insert a lucky. put "life" as collection name
		Lucky lucky = new Lucky();
		lucky.setType("ca$h");
		lucky.setCdate(new Date());
		
		mongoTemplate.save(lucky);
		
		//check id
		log.info(lucky.toString());
		
	}
	
	@Test
	public void testSaveList() {
		
		//insert a lucky. put "life" as collection name
		Lucky lucky1 = new Lucky();
		lucky1.setType("everfree");
		lucky1.setCdate(new Date());
		Lucky lucky2 = new Lucky();
		lucky2.setType("everfree");
		lucky2.setCdate(new Date());
		Lucky lucky3 = new Lucky();
		lucky3.setType("everfree");
		lucky3.setCdate(new Date());
		List<Lucky> list = new ArrayList<Lucky>();
		list.add(lucky1);
		list.add(lucky2);
		list.add(lucky3);
		
		mongoTemplate.insert(list, Lucky.class);
		
		List<Lucky> luckyList = mongoTemplate.find(new Query(Criteria.where("type").is("everfree")), Lucky.class);
		for(Lucky lucky : luckyList){
			log.info(lucky.toString());
		}
		
	}
	
	@Test
	public void testFind() {
		Query query = new Query();
		query.addCriteria(Criteria.where("type").is("ca$h"));
		
		Lucky lucky = mongoTemplate.findOne(query, Lucky.class);
		log.info(lucky.toString());
	}
	
	@Test
	public void testSaveVsInsert() {
		// save vs insert
		Lucky beforeLucky = mongoTemplate.findOne(new Query(Criteria.where("type").is("ca$h")), Lucky.class);
		log.info(beforeLucky.toString());
		beforeLucky.setType("new ca$h");
		beforeLucky.setUdate(new Date());

		// insert는 _id가 이미 존재하면 에러발생, save는 존재하면 update시킴.
		//mongoTemplate.insert(beforeLucky);
		mongoTemplate.save(beforeLucky);
		Lucky afterLucky = mongoTemplate.findOne(new Query(Criteria.where("type").is("new ca$h")), Lucky.class);
		log.info(afterLucky.toString());
	}

}
