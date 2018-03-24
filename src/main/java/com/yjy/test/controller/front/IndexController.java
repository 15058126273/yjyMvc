package com.yjy.test.controller.front;

import com.alibaba.fastjson.JSON;
import com.yjy.test.base.BaseController;
import com.yjy.test.controller.ErrorCode;
import com.yjy.test.entity.front.User;
import com.yjy.test.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController extends BaseController {

    private static final Logger log = LogManager.getLogger(IndexController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model) {
        log.info("index >>>>>>>>>>>>>>>>>>>>");

//        User user = new User();
//        user.setUsername("yjy3");
//        user = userService.save(user);
//        log.info("saved user : {}", user);

        try {
            userService.testTransaction();
        } catch (Exception e) {
            log.info("throw exception > ", e);
        }
        return "front/index/index";
    }

    @RequestMapping(value = "/ajax1", method = RequestMethod.POST)
    public void testAjax(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setUsername("ajax");
        user = userService.save(user);
        ajaxSuccessJson(response, JSON.toJSONString(user));
    }

    @RequestMapping(value = "/ajax2", method = RequestMethod.POST)
    public void testAjax2(HttpServletRequest request, HttpServletResponse response) {
        ajaxErrorJson(response, "error", ErrorCode.ERR_0000);
    }


}
