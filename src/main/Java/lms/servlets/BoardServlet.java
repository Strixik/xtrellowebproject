package lms.servlets;

import lms.service.AdminService;
import lms.service.BoardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet(name = "BoardServlet", urlPatterns = "/board/*")
public class BoardServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(BoardServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BoardService boardView = new BoardService(out);
        HttpSession session = request.getSession();

        log.info("pathInfo:\t" + request.getPathInfo());
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
        HttpSession session = request.getSession();
        BoardService boardView = new BoardService(out);
        AdminService adminView = new AdminService(out);

        log.info("pathInfo:\t" + request.getPathInfo());
        switch (request.getPathInfo()) {
            case "/view":
                boardView.showAllBoards(session);
                break;
            case "/insert-search-notes-url-here":
                boardView.showAllBoardsSearch(request,session);
                break;
            case "/all":
                adminView.showAllBoardsForAdmin();
                break;
        }
    }
}
