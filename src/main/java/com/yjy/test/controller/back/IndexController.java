package com.yjy.test.controller.back;

import com.yjy.test.base.BaseController;
import com.yjy.test.service.UserService;
import com.yjy.test.util.hibernate.OrderBy;
import com.yjy.test.util.hibernate.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {

    private static final Logger log = LogManager.getLogger(IndexController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index.do")
    public String index(HttpServletRequest request, Model model) {
        return "back/index/index";
    }

    @RequestMapping(value = "/hello.do")
    public String hello(HttpServletRequest request, Model model) {
        return "back/index/hello";
    }

}
