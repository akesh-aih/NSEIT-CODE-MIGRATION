package com.nseit.generic.util;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;

public class HSTSFilter implements Filter {

	private Logger logger = LoggerHome.getLogger(getClass());
	
    private int maxAge;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        maxAge = Integer.parseInt(filterConfig.getInitParameter("maxAge"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	try {
	        HttpServletResponse httpResponse = (HttpServletResponse) response;
	        httpResponse.setHeader("Strict-Transport-Security", "max-age=" + maxAge);
	        httpResponse.addHeader("X-Content-Type-Options", "nosniff");
	        httpResponse.addHeader("Content-Security-Policy", "script-src 'unsafe-inline' 'unsafe-eval' 'self'");
	        httpResponse.addHeader("Pragma", "no-cache");
	        httpResponse.addHeader("X-XSS-Protection", "1; mode=block");
	        httpResponse.addHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
	        httpResponse.setDateHeader("Expires", 0);
	        String url = ((HttpServletRequest)request).getRequestURL().toString();
	        String referrer = ((HttpServletRequest) request).getHeader("referer");
	        String warPath = ((HttpServletRequest) request).getContextPath();
	        if(referrer == null && !url.endsWith(warPath+"/")) {
	        	httpResponse.sendRedirect(warPath+"/");
	        }
	        chain.doFilter(request, response);
    	}catch(IllegalStateException ISE) {
    		logger.info("exception supressed because of redirection"+ISE.getMessage());
    	}
    }

    @Override
    public void destroy() {
    }
}
