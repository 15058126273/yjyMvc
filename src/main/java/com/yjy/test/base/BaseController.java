package com.yjy.test.base;

import com.yjy.test.controller.ErrorCode;
import com.yjy.test.util.UnicodeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 控制层父类
 */
public abstract class BaseController extends BaseClass {

    private static final Logger log = LogManager.getLogger(BaseController.class);

    protected void ajaxErrorJson(HttpServletResponse response, String errorInfo, ErrorCode code) {
        ajaxToJson(response, null, errorInfo, 0, code);
    }

    protected void ajaxSuccessJson(HttpServletResponse response, String jsonString) {
        ajaxToJson(response, jsonString, null, 1, null);
    }

    /**
     * ajax 返回 json 数据
     * @param response
     * @param jsonString 待返回的JSON数据
     * @param info 描述信息，一般为有错误时使用
     * @param status 返回数据的状态，1为成功，0为失败
     * @param code 错误代码
     */
    private void ajaxToJson(HttpServletResponse response, String jsonString, String info, Integer status, ErrorCode code) {
        StringBuffer result = new StringBuffer("{\"status\":");
        result.append(status).append(",\"data\":")
                .append( jsonString ).append(",\"info\":\"")
                .append(info == null ? "" : UnicodeUtil.toEncodedUnicode(info.trim(), false)).append("\",\"code\":\"")
                .append(code).append("\"}");

        try {
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Pragma","no-cache");
            response.setDateHeader ("Expires",-1);
            //response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
//            response.getOutputStream().print(result.toString());
            response.getWriter().print(result);
        } catch(IOException e) {
            if (log.isErrorEnabled()) {
                log.error("无法返回请求数据", e);
            }
        }
    }

}
