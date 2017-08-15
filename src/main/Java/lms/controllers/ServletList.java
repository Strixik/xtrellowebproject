package lms.controllers;

import lms.service.ListTemplate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletList", urlPatterns = "/list/*")
public class ServletList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        ListTemplate listView = new ListTemplate(out);
        switch (request.getPathInfo()) {
            case "/":
                session.setAttribute("board_id", request.getParameter("boardid"));
                out.println(session.getAttribute("board_id"));
                response.sendRedirect("/list/view");
                break;

            case "/add":
                if (listView.addListForm(request, session)) {
                    response.sendRedirect("/list/view");
                }
                break;
            case "/del":
                if (listView.deleteList(request)) {
                    response.sendRedirect("/list/view");
                } else {
                    response.sendRedirect("/list/view");
                }
                break;
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ListTemplate listView = new ListTemplate(out);
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("board_id"));
        switch (request.getPathInfo()) {
            case "/view":
                listView.showList(session);
                break;
        }
    }
}
