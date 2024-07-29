package com.aquarius.wizard.resilience4j;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author zhaoyijie
 * @since 2024/7/29 16:52
 */
@Slf4j
public class MDCFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String traceld = httpServletRequest.getHeader("traceId");
            if (traceld == null) {
                traceld = UUID.randomUUID().toString();
            }
            MDC.put("traceId", traceld);
            MDC.put("ts", String.valueOf(new Date().getTime()));
            log.info(httpServletRequest.getRequestURL() + " call received");
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();// 清除 MDC 信息，避免内存进漏和混滑
        }
    }
}
