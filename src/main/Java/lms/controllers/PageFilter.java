package lms.controllers;

import lms.views.UserHtmlViews;

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
    private static Logger log = Logger.getLogger(PageFilter.class.getName());

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

            String top = UserHtmlViews.getInstance().getTopUserView();
            HttpSession session = request.getSession();
            if (session.getAttribute("login") == null && (!request.getServletPath().equals(""))) {
                response.sendRedirect("/");
            }
            if (session.getAttribute("login") != null) {
                top = top.replace("<!-- servletInsert01 -->", UserHtmlViews.getInstance().getMenuBarRight());
                top = top.replace("<!-- currentUser -->", session.getAttribute("login").toString().toUpperCase());
                if (session.getAttribute("login").equals("Strix")) {
                    top = top.replace("hidden", "");
                }
            }

            out.write(top);
            chain.doFilter(req, resp);
            out.write(UserHtmlViews.getInstance().getBottomUserView());
        }
    }


    public void init(FilterConfig config) throws ServletException {
    }

}
