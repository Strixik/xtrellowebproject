package lms.service;

import lms.dao.CRUDRepository.CardDao;
import lms.dao.entity.Card;
import lms.dao.repository.CardImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class CardTemplate {
    public CardTemplate() {
    }

    public void addCard (HttpServletRequest request) {
        try {
            long listId = Long.parseLong(request.getParameter("listid"));
            String cardText = new String(request.getParameter("cardText").getBytes("iso-8859-1"),
                    "UTF-8");
            System.out.println(listId + cardText);
            if (cardText != null && listId != 0 && !cardText.isEmpty() ) {
                Card card = new Card(cardText, listId);
                CardDao cardDao = new CardImpl();
                cardDao.saveCard(card);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public boolean  delCard (HttpServletRequest request) {
        String cardId = request.getParameter("id");

        if (cardId != null && !cardId.isEmpty()) {
            long id = Long.parseLong(cardId);
            CardDao cardDao = new CardImpl();
            cardDao.removeCard(id);
            return true;
        }
        return false;
    }

}
