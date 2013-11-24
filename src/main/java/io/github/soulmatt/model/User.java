package io.github.soulmatt.model;

import java.util.Date;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class User {
	
	public User() {}
	
	public User(String ic, String name, int age) {
		this.setIc(ic);
		this.setName(name);
		this.setAge(age);
	}
	
	@Id
	private String id;
	
	String ic;
	String name;
	int age;
	Date cdate;
	
}
