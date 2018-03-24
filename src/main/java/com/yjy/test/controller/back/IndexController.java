package com.yjy.test.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @RequestMapping(value = "index.do")
    public String index(HttpServletRequest request, Model model) {
        return "back/index/index";
    }

}
