package lms.servlets;


import lms.service.UserService.AdminService;
import lms.service.UserService.LoginService;
import lms.service.UserService.ProfileService;
import lms.service.UserService.RegistrationService;
import lms.views.HtmlViews.BoardHtmlViews;
import lms.views.HtmlViews.PanelHtmlViews;
import lms.views.HtmlViews.UserHtmlViews;
import lms.views.PathHtml;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.*;

@WebServlet(name = "IndexServlet", urlPatterns = "/*", loadOnStartup = 1)
public class IndexServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(IndexServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        LoginService loginView = new LoginService(out);
        RegistrationService registrationView = new RegistrationService(out);
        ProfileService profileView = new ProfileService(out);

        LOGGER.info("pathInfo:\t" + request.getPathInfo());
        switch (request.getPathInfo()) {
            case "/register":
                if (registrationView.checkRegistrationForm(request)) {
                    response.sendRedirect("/");
                }
                break;
            case "/login":
                if (loginView.checkLogin(request, session)) {
                    response.sendRedirect("/board/view");
                }
                break;
            case "/profile-edit":
                if (profileView.checkProfileForm(request)) {
                    response.sendRedirect("/profile-edit");
                }
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        LoginService loginView = new LoginService(out);
        RegistrationService registrationView = new RegistrationService(out);
        ProfileService profileView = new ProfileService(out);
        AdminService adminView = new AdminService(out);

        LOGGER.info("pathInfo:\t" + request.getPathInfo());
        switch (request.getPathInfo()) {
            case "/":
                if (session.getAttribute("login") != null) {
                    response.sendRedirect("/board/view");
                } else {
                    loginView.showLoginForm();
                }
                break;
            case "/logout":
                LOGGER.info("logged out:\t" + session.getAttribute("username"));
                session.removeAttribute("login");
                response.sendRedirect("/");
                break;
            case "/register":
                registrationView.showRegistrationForm();
                break;
            case "/profile-edit":
                profileView.showUserProfileForm(session.getAttribute("login").toString(), session);
                break;
            case "/useradmin":
                adminView.showAllUsers();
                break;
            case "/admintest":
                String login = request.getParameter("login");
                profileView.showUserProfileForm(login, session);
                break;

            default:
                response.sendRedirect("/");
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        /**
         * set path to Html files
         */
        PathHtml pathHTML = PathHtml.getInstance();
        if (pathHTML.getPath().equals("")) {
            pathHTML.setPath(getServletContext().getRealPath("/WEB-INF/html/"));

        }
        /**
         * load partial html files
         */
        UserHtmlViews.getInstance();
        BoardHtmlViews.getInstance();
        PanelHtmlViews.getInstance();
        /**
         * Config Logger
         */
        try {
            Handler fileHandler = new FileHandler(getServletContext().getRealPath("WEB-INF/logs/app.log"));
            fileHandler.setFormatter(new SimpleFormatter());
            Logger.getLogger("").addHandler(fileHandler);
            Logger.getLogger("").addHandler(new ConsoleHandler());
            Logger.getLogger("").setLevel(Level.INFO);
        } catch (IOException e) {
            System.out.println("Problem with log file:\t " + e.toString());
        }
    }
}
