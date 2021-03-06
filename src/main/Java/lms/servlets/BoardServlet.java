package lms.servlets;

import lms.service.BoardService;
import lms.service.UserService.AdminService;

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
    private static final Logger LOGGER = Logger.getLogger(BoardServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        BoardService boardView = new BoardService(out);

        LOGGER.info("pathInfo:\t" + request.getPathInfo());
        switch (request.getPathInfo()) {
            case "/add":
                boardView.addBoardForm(request, session);
                break;
            case "/del":
                boardView.deleteBoard(request);
                break;
        }
        response.sendRedirect("/view");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        BoardService boardView = new BoardService(out);
        AdminService adminView = new AdminService(out);

        LOGGER.info("pathInfo:\t" + request.getPathInfo());
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
