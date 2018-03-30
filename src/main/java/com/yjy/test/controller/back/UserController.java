package com.yjy.test.controller.back;

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
@RequestMapping("/user")
public class UserController extends BaseBackController {

    private static final Logger log = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list.do")
    public String list(Integer pageNo, Integer pageSize, HttpServletRequest request, Model model) {
        try {
            Pagination pagination = userService.getPage(Pagination.cpn(pageNo), Pagination.cps(pageSize), OrderBy.desc("id"));
            model.addAttribute("pagination", pagination);
        } catch (Exception e) {
            log.error("index error", e);
        }
        return page("user", "list", request, model);
    }

}
