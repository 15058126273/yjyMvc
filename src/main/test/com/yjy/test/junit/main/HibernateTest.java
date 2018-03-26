package com.yjy.test.junit.main;

import com.yjy.test.entity.front.User;
import com.yjy.test.service.UserService;
import com.yjy.test.util.hibernate.Pagination;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-03-26 22:25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-hibernate.xml", "classpath:bean-context.xml",
})
public class HibernateTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void test() {
        User user = new User();
        user.setUsername("123");
        userService.save(user);
        user = new User();
        user.setUsername("888");
        userService.saveOrUpdate(user);
        user = new User();
        user.setUsername("999");
        userService.merge(user);

        List<User> list = userService.getAll();
        log.info("list > {}", list);

        user = list.get(0);
        String uniqueName = System.currentTimeMillis() + "";
        user.setUsername(uniqueName);
        userService.update(user);


        Pagination p = userService.getPage(1, 20);
        log.info("totalCount: {}, list: {}", p.getTotalCount(), p.getList());

        user = userService.getUniqueByProperty("username", uniqueName);
        log.info("uniqueUser : {}", user);
    }
}
