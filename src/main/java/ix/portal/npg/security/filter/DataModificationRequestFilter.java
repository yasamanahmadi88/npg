package ix.portal.npg.security.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

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

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        DataModificationRequestWrapper dataModificationRequestWrapper = new DataModificationRequestWrapper((HttpServletRequest) request);

        Throwable problem = null;
        try {
            chain.doFilter(dataModificationRequestWrapper, response);
        } catch (Throwable t) {
            problem = t;
            t.printStackTrace();
        }

        if (problem != null) {
            if (problem instanceof ServletException) throw (ServletException) problem;
            if (problem instanceof IOException) throw (IOException) problem;
            try {
                sendProcessingError(problem, response);
            } catch (Exception e) {
                try {
                    throw e;
                } catch (Exception e1) {
                    e1.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
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

    private void sendProcessingError(Throwable t, ServletResponse response) throws Exception {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {}
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {}
        }
    }

    public static String getStackTrace(Throwable t) throws Exception {
        String stackTrace = null;

        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
            throw ex;
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

    private static final boolean debug = true;
}


