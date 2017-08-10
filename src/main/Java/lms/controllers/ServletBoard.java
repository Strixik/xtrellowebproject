package lms.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletBoard", urlPatterns = "/board")
public class ServletBoard extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.write("<a href=\"#myModal\" id=\"knopka\" class=\"btn btn-default\" data-toggle=\"modal\">Створити дошку</a>\n" +
                "\n" +
                "<!-- HTML-код модального окна-->\n" +
                "<div id=\"myModal\" class=\"modal fade \">\n" +
                "    <form action=\"/board\" method=\"post\">\n" +
                "    <div class=\"modal-dialog\">\n" +
                "        <div class=\"modal-content\">\n" +
                "            <!-- Заголовок модального окна -->\n" +
                "            <div class=\"modal-header\">\n" +
                "                <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>\n" +
                "                <h4 class=\"modal-title\">Введіть назву Дошки:</h4>\n" +
                "            </div>\n" +
                "            <!-- Основное содержимое модального окна -->\n" +
                "            <div class=\"modal-body\">\n" +
                "                <input type=\"text\" class=\"form-control\" id=\"\" name=\"nameBoard\" placeholder=\"Назва дошки\">\n" +
                "            </div>\n" +
                "            <!-- Футер модального окна -->\n" +
                "            <div class=\"modal-footer\">\n" +
                "                <button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">Закрити</button>\n" +
                "                <input type=\"submit\" class=\"btn btn-success\" title=\"Зберегти\" value=\"Зберегти\">\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    </form>\n" +
                "</div>"+"<a href=\"/list\" class=\"boarda\">\n" +
                        "    <div class=\"board\">\n" +
                        "        <h1>тест</h1>\n" +
                        "    </div>\n" +
                        "</a>"
                        +"<a href=\"/list\" class=\"boarda\">\n" +
                        "    <div class=\"board\">\n" +
                        "        <h1>тест</h1>\n" +
                        "    </div>\n" +
                        "</a>"
                        +"<a href=\"/list\" class=\"boarda\">\n" +
                        "    <div class=\"board\">\n" +
                        "        <h1>тест</h1>\n" +
                        "    </div>\n" +
                        "</a>"
                        +"<a href=\"/list\" class=\"boarda\">\n" +
                        "    <div class=\"board\">\n" +
                        "        <h1>тест</h1>\n" +
                        "    </div>\n" +
                        "</a>"
                        +"<a href=\"/list\" class=\"boarda\">\n" +
                        "    <div class=\"board\">\n" +
                        "        <h1>тест</h1>\n" +
                        "    </div>\n" +
                        "</a>"
                        +"<a href=\"/list\" class=\"boarda\">\n" +
                        "    <div class=\"board\">\n" +
                        "        <h1>тест</h1>\n" +
                        "    </div>\n" +
                        "</a>"
                        +"<a href=\"/list\" class=\"boarda\">\n" +
                        "    <div class=\"board\">\n" +
                        "        <h1>тест</h1>\n" +
                        "    </div>\n" +
                        "</a>"
                        +"<a href=\"/list\" class=\"boarda\">\n" +
                        "    <div class=\"board\">\n" +
                        "        <h1>тест</h1>\n" +
                        "    </div>\n" +
                        "</a>"
                        +"<a href=\"/list\" class=\"boarda\">\n" +
                        "    <div class=\"board\">\n" +
                        "        <h1>тест</h1>\n" +
                        "    </div>\n" +
                        "</a>"
                        +"<a href=\"/list\" class=\"boarda\">\n" +
                        "    <div class=\"board\">\n" +
                        "        <h1>тест</h1>\n" +
                        "    </div>\n" +
                        "</a>"
                        +"<a href=\"/list\" class=\"boarda\">\n" +
                        "    <div class=\"board\">\n" +
                        "        <h1>тест</h1>\n" +
                        "    </div>\n" +
                        "</a>"
                  );
    }
}
