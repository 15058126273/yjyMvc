package com.yjy.test.web.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 防Xss攻击 以及 Sql注入
 *
 * @Author yjy
 * @Date 2018-03-25 15:12
 */
public class XssAndSqlFilter implements Filter {

    private static final Logger log = LogManager.getLogger(XssAndSqlFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("XssAndSqlFilter initialized...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        XssAndSqlHttpServletRequestWrapper wrapper = new XssAndSqlHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(wrapper, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("XssAndSqlFilter destroyed...");
    }
}
