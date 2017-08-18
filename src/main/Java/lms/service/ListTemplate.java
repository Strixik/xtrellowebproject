package lms.service;


import lms.dao.CRUD;
import lms.dao.entity.Card;
import lms.dao.entity.Panel;
import lms.dao.repository.CardRepo;
import lms.dao.repository.PanelRepo;
import lms.views.ListHtmlViews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ListTemplate {
    private PrintWriter out;
    public ListTemplate(PrintWriter out) {
        this.out = out;
    }

    public boolean addListForm(HttpServletRequest request, HttpSession session) {
        if (out == null) return false;
        try {
            long boardId = Long.parseLong(session.getAttribute("board_id").toString());
            String nameList = new String(request.getParameter("nameList")
                    .getBytes("iso-8859-1"), "UTF-8");
            System.out.println(boardId + nameList);
            if (boardId != 0L && !nameList.isEmpty()) {
                Panel panel = new Panel(nameList, boardId);
                CRUD<Panel> panelRepo = new PanelRepo();
                panelRepo.save(panel);
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void showList(HttpSession session) {
        if (out == null) return;
        Long boardId = Long.parseLong(session.getAttribute("board_id").toString());
        CRUD<Panel> panelRepo = new PanelRepo();
        CRUD<Card> cardRepo = new CardRepo();

        List<Panel> panels = panelRepo.retrieveAll(boardId);
        out.println(ListHtmlViews.getInstance().getModalButton());
        for (Panel panel : panels) {
            List<Card> cards = cardRepo.retrieveAll(panel.getId());
            String panelTitle = ListHtmlViews.getInstance().getListHtml();
            panelTitle = panelTitle.replace("<!--list-->", panel.getListName());
            panelTitle = panelTitle.replace("listId", String.valueOf(panel.getId()));
            StringBuilder sb = new StringBuilder();
            for (Card c: cards){
                sb.append("<input type=\"radio\" value=\"").append(c.getId())
                        .append("\" name=\"id\">").append("<li class=\"cardClass\">").append(c.getCardText()).append("</li>");
            }
            panelTitle = panelTitle.replace("<!--" + panel.getId() + "your text" + "-->", sb);
            out.println(panelTitle);
        }
    }
    public boolean deleteList(HttpServletRequest request) {
        long listId = Long.parseLong(request.getParameter("listid").toString());
        if (listId > 0L) {
            CRUD<Panel> panelRepo = new PanelRepo();
            panelRepo.remove(listId);
            return true;
        } else return false;
    }
}