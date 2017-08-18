package lms.controllers;

import lms.service.CardTemplate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "ServletCard", urlPatterns = "/card/*")
public class ServletCard extends HttpServlet {
    private static Logger log = Logger.getLogger(ServletCard.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CardTemplate cardView = new CardTemplate();

        log.info("pathInfo:\t" + request.getPathInfo());
        switch (request.getPathInfo()) {
            case "/add":
                cardView.addCard(request);
                response.sendRedirect("/list/view");
                break;
            case "/del":
                if (cardView.delCard(request)) {
                    response.sendRedirect("/list/view");
                } else
                    response.sendRedirect("/list/view");
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("pathInfo:\t" + request.getPathInfo());
        response.sendRedirect("/list/view");
    }
}
