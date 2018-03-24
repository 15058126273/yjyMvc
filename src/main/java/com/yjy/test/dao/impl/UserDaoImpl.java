package com.yjy.test.dao.impl;

import com.yjy.test.base.BaseDaoImpl;
import com.yjy.test.dao.UserDao;
import com.yjy.test.entity.front.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {


}
