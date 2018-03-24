package com.yjy.test.service.impl;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.dao.UserDao;
import com.yjy.test.entity.front.User;
import com.yjy.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    public void setDao(UserDao userDao) {
        super.setDao(userDao);
    }

    public UserDao getDao() {
        return (UserDao) super.getDao();
    }

    public void testTransaction() {
        User user = new User();
        user.setUsername("test001");
        save(user);
        user = new User();
        user.setUsername("test002");
        save(user);
//        if ( true)
//            throw new RuntimeException();
        user = new User();
        user.setUsername("test003");
        save(user);


    }


}
