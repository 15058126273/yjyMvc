package com.yjy.test.service;

import com.yjy.test.base.BaseService;
import com.yjy.test.entity.back.Admin;

/**
 * 管理员
 *
 * @Author yjy
 * @Date 2018-03-29 21:47
 */
public interface AdminService extends BaseService<Admin> {

    /**
     * 管理员登入
     * @param username 用户名
     * @param password 密码
     * @return if 登入成功 {@link Admin} else null
     */
    Admin login(String username, String password);

}
