package lms.service;

import lms.dao.CRUD;
import lms.dao.entity.Card;
import lms.dao.repository.CardRepo;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class CardTemplate {
    public CardTemplate() {
    }

    public void addCard (HttpServletRequest request) {
        try {
            long listId = Long.parseLong(request.getParameter("listid"));
            String cardText = new String(request.getParameter("cardText")
                    .getBytes("iso-8859-1"), "UTF-8");
            System.out.println(listId + cardText);
            if (listId != 0 && !cardText.isEmpty()) {
                Card card = new Card(cardText, listId);
                CRUD<Card> cardCRUD = new CardRepo();
                cardCRUD.save(card);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public boolean  delCard (HttpServletRequest request) {
        String cardId = request.getParameter("id");

        if (cardId != null && !cardId.isEmpty()) {
            long id = Long.parseLong(cardId);
            CRUD<Card> cardRepo = new CardRepo();
            cardRepo.remove(id);
            return true;
        }
        return false;
    }

}
