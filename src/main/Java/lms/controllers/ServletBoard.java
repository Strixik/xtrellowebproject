package lms.controllers;

import lms.dao.CRUDRepository.BoardDao;
import lms.dao.entity.Board;
import lms.dao.repository.BoardImpl;
import lms.service.BoardTemplate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ServletBoard", urlPatterns = "/board")
public class ServletBoard extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BoardTemplate indexView = new BoardTemplate(out);
        HttpSession session = request.getSession();
                       if (indexView.addBoardForm(request,session)) {
                    response.sendRedirect("/board");
                }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BoardTemplate indexView = new BoardTemplate(out);
        HttpSession session = request.getSession();
        indexView.showBoard(session);
    }
}
