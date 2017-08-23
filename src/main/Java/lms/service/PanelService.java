package lms.service;


import lms.dao.CRUD;
import lms.dao.entity.Card;
import lms.dao.entity.Panel;
import lms.dao.repository.CardRepo;
import lms.dao.repository.PanelRepo;
import lms.views.HtmlViews.PanelHtmlViews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;

public class PanelService {
    private static final Logger LOGGER = Logger.getLogger(PanelService.class.getName());
    private PrintWriter out;

    public PanelService(PrintWriter out) {
        this.out = out;
    }

    public boolean addPanelForm(HttpServletRequest request, HttpSession session) {
        try {
            long boardId = Long.parseLong(session.getAttribute("board_id").toString());
            String panelTitle = new String(request.getParameter("nameList").getBytes("UTF-8"));
            LOGGER.info(boardId + " " + panelTitle);
            if (boardId != 0L && !panelTitle.isEmpty()) {
                Panel panel = new Panel(panelTitle, boardId);
                CRUD<Panel> panelRepo = new PanelRepo();
                panelRepo.save(panel);
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.severe("UnsupportedEncodingException " + e.toString());
        }
        return false;
    }

    public void showPanel(HttpSession session) {
        Long boardId = Long.parseLong(session.getAttribute("board_id").toString());
        CRUD<Panel> panelRepo = new PanelRepo();
        CRUD<Card> cardRepo = new CardRepo();

        List<Panel> panels = panelRepo.retrieveAll(boardId);
        out.println(PanelHtmlViews.getInstance().getListAddModalWindow());
        for (Panel panel : panels) {
            List<Card> cards = cardRepo.retrieveAll(panel.getId());
            String panelTitle = PanelHtmlViews.getInstance().getListHtml();
            panelTitle = panelTitle.replace("<!--list-->", panel.getPanelTitle());
            panelTitle = panelTitle.replace("listId", String.valueOf(panel.getId()));
            StringBuilder sBuilder = new StringBuilder();
            for (Card c: cards){
                sBuilder.append("<input type=\"radio\" value=\"").append(c.getId()).append("\" name=\"id\">")
                        .append("<li class=\"cardClass\">").append(c.getCardText()).append("</li>");
            }
            panelTitle = panelTitle.replace("<!--" + panel.getId() + "your text" + "-->", sBuilder);
            out.println(panelTitle);
        }
    }

    public boolean deletePanel(HttpServletRequest request) {
        long listId = Long.parseLong(request.getParameter("listid"));
        if (listId > 0L) {
            CRUD<Panel> panelRepo = new PanelRepo();
            panelRepo.remove(listId);
            return true;
        } else return false;
    }
}