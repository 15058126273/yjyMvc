package com.yjy.test.junit.main;

import com.yjy.test.util.MD5Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JunitTest {

    private static final Logger log = LogManager.getLogger(JunitTest.class);

    @Test
    public void tryLog () {
        log.info(MD5Util.MD5Encode("admin"));
    }

}
