package lms.controllers;



import lms.service.UserTemplate;

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

@WebServlet(name = "ServletDispatcher", urlPatterns = "/*", loadOnStartup = 1)
public class ServletDispatcher extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        UserTemplate indexView = new UserTemplate(out);
        HttpSession session = request.getSession();

        switch (request.getPathInfo()) {
            case "/register":
                if (indexView.checkRegistrationForm(request)) {
                    indexView.showRegisterForm();
                }
                break;
            case "/login":
                // перевіряє логін форму, якщо неправильно введені дані повертає форму для перезаповнення
                if (indexView.checkLogin(request, session)) {
                    response.sendRedirect("/board");
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

        switch (request.getPathInfo()) {
            case "/":
                if(session.getAttribute("login") != null) {
                    response.sendRedirect("/board");
                } else {
                    indexView.showLoginForm();
                }
                break;
            case "/logout":
                session.removeAttribute("login");
                response.sendRedirect("/");
                break;
            case "/register":
                indexView.showRegisterForm();
                break;
            case "/profile-edit":
                indexView.showUserProfileForm(session.getAttribute("login").toString());
                break;
            default:
                response.sendRedirect("/");
        }
}


    @Override
    public void init() throws ServletException {
        super.init();
        //set path
        PathHtml pathHTML = PathHtml.getInstance();
        if(pathHTML.getPath().equals("")) {
            pathHTML.setPath(getServletContext().getRealPath("/WEB-INF/html/"));

        }

        //load partial html files
        UserHtmlViews.getInstance();
    }
}
