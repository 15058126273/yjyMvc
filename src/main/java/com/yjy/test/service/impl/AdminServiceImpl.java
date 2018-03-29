package com.yjy.test.service.impl;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.dao.AdminDao;
import com.yjy.test.entity.back.Admin;
import com.yjy.test.service.AdminService;
import com.yjy.test.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理员
 *
 * @Author yjy
 * @Date 2018-03-29 21:47
 */
@Service
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Autowired
    public void setDao(AdminDao dao) {
        super.setDao(dao);
    }

    public AdminDao getDao() {
        return (AdminDao) super.getDao();
    }

    public Admin login(String username, String password) {
        Admin admin = getDao().getUniqueByProperty("username", username);
        if (admin != null && admin.getPassword().equals(MD5Util.MD5Encode(password))) {
            return admin;
        }
        return null;
    }

}
