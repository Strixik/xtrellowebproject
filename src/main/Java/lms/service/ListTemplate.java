package lms.service;


import lms.dao.CRUDRepository.CardDao;
import lms.dao.CRUDRepository.ListDao;
import lms.dao.entity.Card;
import lms.dao.entity.Panel;
import lms.dao.repository.CardImpl;
import lms.dao.repository.ListImpl;
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
            String nameList = new String(request.getParameter("nameList").getBytes("iso-8859-1"),
                    "UTF-8");
            System.out.println(boardId + nameList);
            if (nameList != null && boardId != 0L) {
                Panel list = new Panel(nameList, boardId);
                ListDao listDao = new ListImpl();
                listDao.saveList(list);
                //          session.setAttribute("board_id",list.getId());
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
        ListDao listDao = new ListImpl();

        CardDao cardDao = new CardImpl();

        List<Panel> lists = listDao.showAllLists(boardId);
        out.println(ListHtmlViews.getInstance().getModalBottom());
        for (Panel panel : lists) {
            List<Card> cards = cardDao.showAllCards(panel.getId());
            String listName = ListHtmlViews.getInstance().getListName();
            listName = listName.replace("<!--list-->", panel.getListName());
            listName = listName.replace("listId", String.valueOf(panel.getId()));
            StringBuilder sb = new StringBuilder();
            for (Card c: cards){
             sb.append("<li>").append(c.getCardText()).append("</li>");
            }
            listName = listName.replace("<!--"+panel.getId()+"your text"+"-->", sb );

            out.println(listName);
        }
    }
    public boolean deleteList(HttpServletRequest request) {
        long listId = Long.parseLong(request.getParameter("listid").toString());
        if (listId > 0L) {
            ListDao listDao = new ListImpl();
            listDao.removeList(listId);
            return true;
        } else return false;
    }
}