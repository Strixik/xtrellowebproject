package lms.controllers;

import lms.service.CardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "CardServlet", urlPatterns = "/card/*")
public class CardServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(CardServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CardService cardView = new CardService();

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
