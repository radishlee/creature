package com.lesports.gene.vct.service;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Timeout;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class BaseITTest extends TestCase {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * please store Starter or RuntimeConfig in a static final field if you want
	 * to use artifact store caching (or else disable caching)
	 */
	private static final MongodStarter starter = MongodStarter
			.getDefaultInstance();

	private static MongodExecutable mongodExe;
	private static MongodProcess mongop;
	@Autowired
	Environment env;
	
	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void initializeDB() throws IOException {
		//初始化一个内存mongodb
		mongodExe = starter.prepare(new MongodConfigBuilder()
				.version(Version.Main.V2_6)
				.timeout(new Timeout())
				.net(new Net(27017, Network.localhostIsIPv6())).build());
		mongop = mongodExe.start();
	}

	
	@Override
    protected void setUp() throws Exception {
		super.setUp();
	}

	@AfterClass
	public static void shutdownDB() throws InterruptedException {
		mongop.stop();
	}
	
	@Test
	@Ignore
	public void ignoreMe() {}
}
