package com.yjy.test.controller.front;

import com.yjy.test.base.BaseController;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

import static com.yjy.test.constants.SessionConstants.KEY_CURRENT_USER;

/**
 * 前台控制层父类
 *
 * @Author yjy
 * @Date 2018-03-29 20:49
 */
public class BaseFrontController extends BaseController {

    /**
     * 返回模板页面
     * @param dir 前台模板目录
     * @param pageName 模板名称
     * @param request 请求
     * @param model 模型数据
     * @return 模板
     */
    @Override
    protected String page(String dir, String pageName, HttpServletRequest request, Model model) {
        model.addAttribute("base", "");
        model.addAttribute("user", request.getSession().getAttribute(KEY_CURRENT_USER));
        return super.page("front/" + dir, pageName, request, model);
    }

}
