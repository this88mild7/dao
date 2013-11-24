package io.github.soulmatt.dao;

import java.util.List;

import io.github.soulmatt.model.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MongoDao {

	@Autowired
	private MongoOperations mongoOperations;
	
	public void save(User user){
		mongoOperations.save(user);
	}
	
	public void save(List<User> listOfUser){
		mongoOperations.insert(listOfUser, User.class);
	}
	
	public List<User> list(){
		return mongoOperations.find(new Query(), User.class);
	}
	
}
