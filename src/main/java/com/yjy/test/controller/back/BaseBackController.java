package com.yjy.test.controller.back;

import com.yjy.test.base.BaseController;
import com.yjy.test.controller.ErrorCode;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

import static com.yjy.test.constants.SessionConstants.KEY_CURRENT_ADMIN;

/**
 * 后台控制层父类
 *
 * @Author yjy
 * @Date 2018-03-29 20:38
 */
public class BaseBackController extends BaseController {

    /**
     * 返回模板页面
     * @param dir 后台模板目录
     * @param pageName 模板名称
     * @param request 请求
     * @param model 模型数据
     * @return 模板
     */
    @Override
    protected String page(String dir, String pageName, HttpServletRequest request, Model model) {
        model.addAttribute("base", "/manager");
        model.addAttribute("admin", request.getSession().getAttribute(KEY_CURRENT_ADMIN));
        return super.page("back/" + dir, pageName, request, model);
    }

    protected String pageError(String errMsg, ErrorCode errCode, HttpServletRequest request, Model model) {
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("errCode", errCode);
        String referer = request.getHeader("Referer");
        model.addAttribute("origin", notBlank(referer) ? referer : "/manager/index.do");
        return page("util", "error", request, model);
    }

}
