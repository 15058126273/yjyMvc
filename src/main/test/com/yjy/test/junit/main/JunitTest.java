package com.yjy.test.junit.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JunitTest {

    private static final Logger log = LogManager.getLogger(JunitTest.class);

    @Test
    public void tryLog () {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("application.xml", "spring-quartz.xml");
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

}
