package lms.service;

import lms.dao.CRUD;
import lms.dao.entity.Card;
import lms.dao.repository.CardRepo;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

public class CardService {
    private static final Logger LOGGER = Logger.getLogger(CardService.class.getName());

    public CardService() {
    }

    public void insertCard(HttpServletRequest request) {
        try {
            long listId = Long.parseLong(request.getParameter("listid"));
            String cardText = new String(request.getParameter("cardText").getBytes("UTF-8"));
            LOGGER.info(listId + " " + cardText);
            if (listId != 0 && !cardText.isEmpty()) {
                Card card = new Card(cardText, listId);
                CRUD<Card> cardCRUD = new CardRepo();
                cardCRUD.save(card);
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.severe("UnsupportedEncodingException " + e.toString());
        }
    }

    public boolean deleteCard(HttpServletRequest request) {
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
