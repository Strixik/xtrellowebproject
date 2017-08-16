package lms.controllers;

import lms.service.CardTemplate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ServletCard", urlPatterns = "/card/*")
public class ServletCard extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CardTemplate cardView = new CardTemplate();
        switch (request.getPathInfo()) {
            case "/add":
                cardView.addCard(request);
                response.sendRedirect("/list/view");
                break;
            case "/del":
                if (cardView.delCard(request)){
                response.sendRedirect("/list/view");
                } else
                response.sendRedirect("/list/view");
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/list/view");
    }
}
