package com.lesports.gene.vct.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration
@ComponentScan("com.lesports.gene")
@PropertySource(value = {"classpath:config-service.properties"})
public class TestConfig extends ServiceAppConfig {
	/**
	 * 在测试情况下替换掉数据库连接
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public @Bean MongoDbFactory mongo() throws Exception {
		MongoClient mongo = new MongoClient("127.0.0.1", 27017);
		String dbname = env.getProperty("dbname");
		return new SimpleMongoDbFactory(mongo, dbname);
	}
}
