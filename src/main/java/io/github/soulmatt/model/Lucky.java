package io.github.soulmatt.model;

import java.util.Date;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "life")
@Data
public class Lucky {
	
	@Id
	private String id;
 
	String type;
	Date cdate;
	Date udate;

}
