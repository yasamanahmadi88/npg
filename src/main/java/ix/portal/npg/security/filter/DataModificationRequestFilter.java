package ix.portal.npg.security.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: sadeghi
 * Date: Aug 22, 2010
 * Time: 9:47:31 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Order(1)
public class DataModificationRequestFilter extends GenericFilterBean {

    private FilterConfig filterConfig = null;

    public DataModificationRequestFilter() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        DataModificationRequestWrapper wrappedRequest = new DataModificationRequestWrapper((HttpServletRequest) request);
        chain.doFilter(wrappedRequest, response);
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {}

    public String toString() {
        if (filterConfig == null) return ("DataModificationRequestFilter()");
        StringBuffer sb = new StringBuffer("DataModificationRequestFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }


    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

    private static final boolean debug = true;
}


