package com.yjy.test.service;

import com.yjy.test.base.BaseService;
import com.yjy.test.entity.front.User;

public interface UserService extends BaseService<User> {


    void testTransaction();

}
