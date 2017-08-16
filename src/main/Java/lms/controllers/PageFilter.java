package lms.controllers;

import lms.views.UserHtmlViews;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "PageFilter", value = {"/*", "/board/*", "/list/*"})
public class PageFilter implements Filter {
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
          top = top.replace("<!-- servletInsert01 -->", UserHtmlViews.getInstance().getLogoutButton());
            top = top.replace("<!-- servletInsert04 -->", "<a class=\"btn btn-primary navbar-right\"aria-label=\"Right Align\">Логін Користувача:  </a>");
            top = top.replace("<!-- servletInsert05 -->", " <a href=\"/profile-edit\" class=\"btn btn-primary navbar-right\"aria-label=\"Right Align\">" + session.getAttribute("login").toString().toUpperCase() + "</a>");
            if (session.getAttribute("login").equals("Strix")) {
               top = top.replace("hidden", "" );
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
