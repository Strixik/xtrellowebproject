package lms.controllers;

import lms.service.BoardTemplate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletBoard", urlPatterns = "/board/*")
public class ServletBoard extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BoardTemplate boardView = new BoardTemplate(out);
        HttpSession session = request.getSession();
        switch (request.getPathInfo()) {
            case "/add":
                if (boardView.addBoardForm(request, session)) {
                    response.sendRedirect("/view");
                } else {
            response.sendRedirect("/view");
                }
                break;
            case "/del":
                if (boardView.deleteBoard(request)) {
                    response.sendRedirect("/view");
                } else {
                    response.sendRedirect("/view");
                }
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BoardTemplate boardView = new BoardTemplate(out);
        HttpSession session = request.getSession();
         switch (request.getPathInfo()) {
            case "/view":
                boardView.showBoard(session);
                break;
             case "/all":
                 boardView.showBoardAllForAdmin();
                 break;
                }
    }
}
