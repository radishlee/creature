package com.lunchtech.vlbs.service;

import java.util.Arrays;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
//import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
//import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
//import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
//import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoCredential;
//import com.mongodb.ReadPreference;
//import com.mongodb.ServerAddress;
//import com.mongodb.WriteConcern;

/**
 * service层引入
 *
 * @author zhoujie
 */
@Configuration
@ComponentScan("com.lunchtech.vlbs")
@PropertySource(value = {"classpath:config-service.properties"})
public class ServiceAppConfig {

    private Logger logger = LoggerFactory.getLogger(ServiceAppConfig.class);
    @Autowired
    Environment env;

    private static final int ENV_MODE_PRO = 1;//生产环境
    private static final int ENV_MODE_DEV = 2;//开发环境
    private static final int ENV_MODE_TEST = 3;//测试环境

    /**
     * 数据库连接
     *
     * @return
     * @throws Exception
     */
//    @Bean
//    public MongoDbFactory mongo() throws Exception {
//        //客户端对象
//        MongoClient mongo = null;
//
//        String dbname = null;
//        //不同的环境用不同的数据库连接
//        int mode = Integer.parseInt(env.getProperty("env.mode"));
//        switch (mode) {
//            case ENV_MODE_PRO: {
//                //数据库名称
//                dbname = env.getProperty("dbname");
//                //服务器列表
//                List<ServerAddress> serverList = Arrays.asList(
//                        new ServerAddress(env.getProperty("master.host"), Integer.parseInt(env.getProperty("master.port"))),
//                        new ServerAddress(env.getProperty("slave1.host"), Integer.parseInt(env.getProperty("slave1.port"))),
//                        new ServerAddress(env.getProperty("slave2.host"), Integer.parseInt(env.getProperty("slave2.port"))));
//
//
//                mongo = new MongoClient(serverList, Arrays.asList(
//                        MongoCredential.createScramSha1Credential(
//                                env.getProperty("mongodb.username"), dbname,
//                                ((String) env.getProperty("mongodb.password")).toCharArray())));
//                break;
//            }
//            case ENV_MODE_DEV: {
//                //数据库名称
//                dbname = env.getProperty("testdb.dbname");
//                //服务器列表
//                List<ServerAddress> serverList = Arrays.asList(
//                        new ServerAddress(env.getProperty("testdb.master.host"), Integer.parseInt(env.getProperty("testdb.master.port"))),
//                        new ServerAddress(env.getProperty("testdb.slave1.host"), Integer.parseInt(env.getProperty("testdb.slave1.port"))),
//                        new ServerAddress(env.getProperty("testdb.slave2.host"), Integer.parseInt(env.getProperty("testdb.slave2.port"))));
//
//
//                mongo = new MongoClient(serverList);
//                break;
//            }
//            case ENV_MODE_TEST: {
//                //数据库名称
//                dbname = env.getProperty("testdb.dbname");
//                //服务器列表
//                List<ServerAddress> serverList = Arrays.asList(
//                        new ServerAddress(env.getProperty("testdb.master.host"), Integer.parseInt(env.getProperty("testdb.master.port"))),
//                        new ServerAddress(env.getProperty("testdb.slave1.host"), Integer.parseInt(env.getProperty("testdb.slave1.port"))),
//                        new ServerAddress(env.getProperty("testdb.slave2.host"), Integer.parseInt(env.getProperty("testdb.slave2.port"))));
//
//
//                mongo = new MongoClient(serverList);
//                break;
//            }
//        }
//
//        //首选从服务器
//        mongo.setReadPreference(ReadPreference.secondaryPreferred());
//        //确认写入再返回
//        mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);
//
//        return new SimpleMongoDbFactory(mongo, dbname);
//    }

    /**
     * 数据库模板操作
     *
     * @return
     * @throws Exception
     */
//    @Bean
//    public MongoTemplate mongoTemplate() throws Exception {
//        MongoDbFactory factory = mongo();
//        //remove _class
//        MappingMongoConverter converter =
//                new MappingMongoConverter(new DefaultDbRefResolver(factory), new MongoMappingContext());
//        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
//        return new MongoTemplate(factory, converter);
//    }


    /**
     * 配置文件加载
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * 公用的httpclient
     *
     * @return
     * @throws Exception
     */
    @Bean
    public HttpClient httpClient() throws Exception {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(Integer.parseInt(env.getProperty("hc.maxTotal")));

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        return httpClient;
    }

}
