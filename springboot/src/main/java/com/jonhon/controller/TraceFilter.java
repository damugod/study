package com.jonhon.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * TraceID Filter
 * @author luis
 */
@Slf4j
public class TraceFilter implements Filter {


    private final String sysId;

    public TraceFilter(String sysId){
        this.sysId = sysId;
        log.info("TraceFilter load");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        MDC.put(RdcConstant.SYS_ID,sysId);
        log.info("TraceFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        //从请求头中获取traceId
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String traceId = request.getHeader(RdcConstant.TRACE_ID);
        // 不存在就生成一个
        if (traceId == null || "".equals(traceId)) {
            traceId = UUID.randomUUID().toString();
        }
        // 放入MDC中
        MDC.put(RdcConstant.TRACE_ID, traceId);
        MDC.put(RdcConstant.SYS_ID,sysId);
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

}
