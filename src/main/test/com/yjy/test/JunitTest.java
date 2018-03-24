package com.yjy.test;

import com.yjy.test.dao.UserDao;
import com.yjy.test.entity.front.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JunitTest {

    private static final Logger log = LogManager.getLogger(JunitTest.class);

    @Test
    public void tryLog () {
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
//        SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");

//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        User user = new User();
//        user.setUsername("yjy");
//        session.save(user);
//
//        Admin admin = new Admin();
//        admin.setUsername("admin");
//        session.save(admin);
//
//        Log log = new Log();
//        log.setRemark("login");
//        session.save(log);
//
//        UserDetail userDetail = new UserDetail();
//        userDetail.setRemark("gogogo");
//        session.save(userDetail);
//
//        InCrease inCrease = new InCrease();
//        inCrease.setRemark("test");
//        session.save(inCrease);
//
//        transaction.commit();
//        session.close();

        UserDao baseDao = (UserDao)context.getBean("userDao");
        User user = new User();
        user.setUsername("yjy2");
        user = baseDao.save(user);
        log.info("saved user : {}", user);
    }

}
