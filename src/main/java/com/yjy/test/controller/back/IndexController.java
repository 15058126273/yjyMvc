package com.yjy.test.controller.back;

import com.yjy.test.controller.ErrorCode;
import com.yjy.test.entity.back.Admin;
import com.yjy.test.service.AdminService;
import com.yjy.test.service.UserService;
import com.yjy.test.util.MD5Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

import static com.yjy.test.constants.SessionConstants.KEY_CURRENT_ADMIN;

/**
 *
 */
@Controller
public class IndexController extends BaseBackController {

    private static final Logger log = LogManager.getLogger(IndexController.class);

    @Autowired
    private AdminService adminService;

    /**
     * 去后台首页
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.do")
    public String index(HttpServletRequest request, Model model) {
        return page("index", "index", request, model);
    }

    /**
     * 去登入页
     * @param request 请求
     * @param model 模型数据
     * @return 模板页面
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public String toLogin(HttpServletRequest request, Model model) {
        return page("index", "login", request, model);
    }

    /**
     * 管理员登入
     * @param username 用户名
     * @param password 密码
     * @param request 请求
     * @param model 模型数据
     * @return
     *  登入成功 -> 后台首页
     *  登入失败 -> 登入页
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String login(String username, String password, HttpServletRequest request, Model model) {
        String info;
        ErrorCode code;
        try {
            if (notBlank(username) && notBlank(password)) {
                Admin admin = adminService.login(username, password);
                if (admin != null) {
                    request.getSession().setAttribute(KEY_CURRENT_ADMIN, admin);
                    return "redirect:/manager/index.do";
                } else {
                    info = "用户名或密码错误";
                    code = ErrorCode.ERR_LOGIN_FAILED;
                }
            } else {
                info = "参数不完整";
                code = ErrorCode.ERR_PARAM;
            }
        } catch (Exception e) {
            info = "登入出错了";
            code = ErrorCode.ERR_SYSTEM;
            log.error("login error", e);
        }
        model.addAttribute("errMsg", info);
        model.addAttribute("errCode", code);
        return toLogin(request, model);
    }

    /**
     * 返回后台欢迎页
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/hello.do")
    public String hello(HttpServletRequest request, Model model) {
        return page("index", "hello", request, model);
    }

}
