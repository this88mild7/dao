package io.github.soulmatt.mongodb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mongodb.xml" })
@Slf4j
public class TestGridFs {

	@Autowired
	private GridFsOperations gridFsOperation;

	@Test
	public void testInsert() {

		DBObject metaData = new BasicDBObject();
		metaData.put("extra1", "anything 1");
		metaData.put("extra2", "anything 2");

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("/vcredist.bmp");
			gridFsOperation.store(inputStream, "another.png", "image/png", metaData);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		log.info("Done");
	}

	@Test
	public void testRead() {

		List<GridFSDBFile> result = gridFsOperation.find(new Query().addCriteria(Criteria.where("filename").is("testing.png")));

		for (GridFSDBFile file : result) {
			try {
				log.info(file.getFilename());
				log.info(file.getContentType());

				// save as another image
				file.writeTo("new-testing.png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		log.info("Done");
	}

}
