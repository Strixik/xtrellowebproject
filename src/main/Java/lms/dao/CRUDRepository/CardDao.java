package lms.dao.CRUDRepository;

import lms.dao.entity.Card;

import java.util.List;


public interface CardDao {
    void saveCard (Card card);
    void removeCard(long id);
    List<Card> showAllCards(long listId);
}
