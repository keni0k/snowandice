package com.example.utils.cache;

import groovy.transform.TypeChecked;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@TypeChecked
public class CacheManifestFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String reqUrl = request.getRequestURL().toString();
        HttpServletResponse response = (HttpServletResponse) res;
        if (reqUrl.endsWith("cache.manifest")) {
            log.debug("Adding no-cache control to manifest file '{}'", reqUrl);
            response.setHeader("Cache-Control", "no-cache");
        } else {
            log.debug("Not adding cache control to '{}'", reqUrl);
        }
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}