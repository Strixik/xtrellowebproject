package lms.controllers;


import lms.service.UserTemplate;
import lms.views.BoardHtmlViews;
import lms.views.ListHtmlViews;
import lms.views.PathHtml;
import lms.views.UserHtmlViews;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.*;

@WebServlet(name = "ServletDispatcher", urlPatterns = "/*", loadOnStartup = 1)
public class ServletDispatcher extends HttpServlet {
    private static Logger log = Logger.getLogger(ServletDispatcher.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        UserTemplate indexView = new UserTemplate(out);
        HttpSession session = request.getSession();

        log.info("pathInfo:\t" + request.getPathInfo());
        switch (request.getPathInfo()) {
            case "/register":
                if (indexView.checkRegistrationForm(request)) {
                    response.sendRedirect("/");
                }
                break;
            case "/login":
                if (indexView.checkLogin(request, session)) {
                    response.sendRedirect("/board/view");
                }
                break;
            case "/profile-edit":
                if (indexView.checkProfileForm(request)) {
                    response.sendRedirect("/profile-edit");
                }
                break;
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        UserTemplate indexView = new UserTemplate(out);

        log.info("pathInfo:\t" + request.getPathInfo());
        switch (request.getPathInfo()) {
            case "/":
                if (session.getAttribute("login") != null) {
                    response.sendRedirect("/board/view");
                } else {
                    indexView.showLoginForm();
                }
                break;
            case "/logout":
                log.warning("logged out:\t" + session.getAttribute("username"));
                session.removeAttribute("login");
                response.sendRedirect("/");
                break;
            case "/register":
                indexView.showRegisterForm();
                break;
            case "/profile-edit":
                indexView.showUserProfileForm(session.getAttribute("login").toString());
                break;
            case "/useradmin":
                indexView.showAllUsers();
                break;
            case "/admintest":
                String login = request.getParameter("login");
                indexView.showUserProfileForm(login);
                break;

            default:
                response.sendRedirect("/");
        }
    }


    @Override
    public void init() throws ServletException {
        super.init();
        //set path to Html files
        PathHtml pathHTML = PathHtml.getInstance();
        if (pathHTML.getPath().equals("")) {
            pathHTML.setPath(getServletContext().getRealPath("/WEB-INF/html/"));

        }
        //load partial html files
        UserHtmlViews.getInstance();
        BoardHtmlViews.getInstance();
        ListHtmlViews.getInstance();
        //logger config
        try {
            Handler fileHandler = new FileHandler(getServletContext().getRealPath("WEB-INF/logs/app.log"));
            fileHandler.setFormatter(new SimpleFormatter());
            Logger.getLogger("").addHandler(fileHandler);
            Logger.getLogger("").addHandler(new ConsoleHandler());
            Logger.getLogger("").setLevel(Level.INFO);
        } catch (IOException e) {
            log.severe("problem with log file: " + e);
        }
    }
}
