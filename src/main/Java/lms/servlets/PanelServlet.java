package lms.servlets;

import lms.service.PanelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet(name = "PanelServlet", urlPatterns = "/list/*")
public class PanelServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(PanelServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        PanelService listView = new PanelService(out);

        LOGGER.info("pathInfo:\t" + request.getPathInfo());
        switch (request.getPathInfo()) {
            case "/":
                session.setAttribute("board_id", request.getParameter("boardid"));
                out.println(session.getAttribute("board_id"));
                break;
            case "/add":
                listView.addListForm(request, session);
                break;
            case "/del":
                listView.deleteList(request);
                break;
        }
        response.sendRedirect("/list/view");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        PanelService listView = new PanelService(out);

        LOGGER.info("pathInfo:\t" + request.getPathInfo());
        switch (request.getPathInfo()) {
            case "/view":
                listView.showList(session);
                break;
        }
    }
}
