package lms.service;


import lms.dao.CRUD;
import lms.dao.entity.Card;
import lms.dao.entity.Panel;
import lms.dao.repository.CardRepo;
import lms.dao.repository.PanelRepo;
import lms.service.helpers.Helper;
import lms.views.HtmlViews.PanelHtmlViews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PanelService {
    private static final Logger LOGGER = Logger.getLogger(PanelService.class.getName());
    private PrintWriter out;

    public PanelService(PrintWriter out) {
        this.out = out;
    }

    public void addListForm(HttpServletRequest request, HttpSession session) {
        try {
            long boardId = Long.parseLong(session.getAttribute("board_id").toString());
            String nameList = Helper.requestParameter("nameList", request);
            LOGGER.info(boardId + " " + nameList);
            if (boardId != 0L && !nameList.isEmpty()) {
                Panel panel = new Panel(nameList, boardId);
                CRUD<Panel> panelRepo = new PanelRepo();
                panelRepo.save(panel);
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.severe("UnsupportedEncodingException " + e.toString());
        }
    }

    public void showList(HttpSession session) {
        if (out == null) return;
        Long boardId = Long.parseLong(session.getAttribute("board_id").toString());
        CRUD<Panel> panelRepo = new PanelRepo();
        CRUD<Card> cardRepo = new CardRepo();

        List<Panel> panels = panelRepo.retrieveAll(boardId);
        out.println(PanelHtmlViews.getInstance().getListAddModalWindow());
        StringBuilder smpBuilder = new StringBuilder();
        for (Panel panel : panels) {
            smpBuilder.append("<option>").append(panel.getPanelTitle()).append("</option>");
        }
        for (Panel panel : panels) {
            List<Card> cards = cardRepo.retrieveAll(panel.getId());
            String panelTitle = PanelHtmlViews.getInstance().getListHtml();
            panelTitle = panelTitle.replace("<!--list-->", panel.getPanelTitle());
            panelTitle = panelTitle.replace("listId", String.valueOf(panel.getId()));
            StringBuilder sBuilder = new StringBuilder();
            StringBuilder mBuilder = new StringBuilder();
            StringBuilder smcBuilder = new StringBuilder();

            for (Card c: cards){
                sBuilder.append("<li class=\"cardClass\">").append("<input type=\"radio\" class=\"bad\" value=\"")
                        .append(c.getId()).append("\" name=\"id\">").append(c.getCardText()).append("</li>");
                mBuilder.append(c.getCardText()).append("\n");
                smcBuilder.append("<option>").append(c.getCardText()).append("</option>");

            }
            panelTitle = panelTitle.replace("<!--" + panel.getId() + "your text" + "-->", sBuilder);
            panelTitle = panelTitle.replace("<!--list text-->", mBuilder);
            panelTitle = panelTitle.replace("<!--card option-->", smcBuilder);
            panelTitle = panelTitle.replace("<!--list option-->", smpBuilder);
            out.println(panelTitle);
        }

    }
    public void deleteList(HttpServletRequest request) {
        long listId = Long.parseLong(request.getParameter("listid"));
        if (listId > 0L) {
            CRUD<Panel> panelRepo = new PanelRepo();
            panelRepo.remove(listId);
        }
    }
    public void editingList(HttpServletRequest request) throws UnsupportedEncodingException {


            long listId = Long.parseLong(Helper.requestParameter("listid", request));
            String listNameNew = Helper.requestParameter("listNameNew", request);
            String listText = Helper.requestParameter("listText", request);
            PanelRepo panelRepo = new PanelRepo();
            panelRepo.update(listId,listNameNew);
            CardRepo cardRepo = new CardRepo();
            List<String> temp = new ArrayList<>();
            List<Card> temp1 = cardRepo.retrieveAll(listId);
            List<Card> temp2 = new ArrayList<Card>();

                int i= 0;
                for (String s : listText.split("\n")) {
                 Iterator<Card> iter = temp1.iterator();
                    while(iter.hasNext()){
                    // cardRepo.update(iter.next().getId(),s,iter.next().getListId());
                        System.out.println(iter.next().getId()+ s + iter.next().getListId());
                    }
            }
        System.out.println(temp1);
          /*  for (int j =0; j < temp.size(); ++j){
                System.out.println(temp.get(j));
                System.out.println(temp1.get(i).getId());
                System.out.println(temp1.get(i).getListId());
               *//* Card card = new Card(temp1.get(i).getId(), temp.get(j), temp1.get(i).getListId());
                temp2.add(card);*//*
                cardRepo.update(temp1.get(i).getId(),temp.get(j),temp1.get(i).getListId());
                ++i;
                }
        System.out.println(temp2);*/



    }
}