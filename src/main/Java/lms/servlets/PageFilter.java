package lms.servlets;

import lms.views.HtmlViews.UserHtmlViews;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebFilter(filterName = "PageFilter", value = {"/*", "/board/*", "/list/*"})
public class PageFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(PageFilter.class.getName());

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String path = request.getRequestURI();
        if (path.startsWith("/testajax/")) {
            chain.doFilter(request, response);
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();

            String top = UserHtmlViews.getInstance().getIndexTop();
            String bottom = UserHtmlViews.getInstance().getIndexBottom();

            HttpSession session = request.getSession();

            if (session.getAttribute("login") == null && (!request.getServletPath().equals(""))) {
                response.sendRedirect("/");
            }
            if (session.getAttribute("login") != null) {
                top = top.replace("<!-- servletInsert01 -->", UserHtmlViews.getInstance().getMenuRight());
                top = top.replace("<!-- currentUser -->", session.getAttribute("login").toString().toUpperCase());
                bottom = bottom.replace("<!--LoginUserName-->", session.getAttribute("login").toString());
                if (session.getAttribute("status").equals("admin")) {
                    top = top.replace("hidden", "");
                }
            }

            out.write(top);
            chain.doFilter(req, resp);
            out.write(bottom);
        }
    }


    public void init(FilterConfig config) throws ServletException {
    }

}
