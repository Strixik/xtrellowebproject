package lms.dao.entity;

public class Card {
    private long id;
    private String cardText;
    private long listId;

    public Card(long id, String cardText, long listId) {
        this.id = id;
        this.cardText = cardText;
        this.listId = listId;
    }

    public Card(String cardText, long listId) {
<<<<<<< HEAD
<<<<<<< HEAD
        this.id = 0L;
=======
>>>>>>> d92383d42685fd4343d6a90af958558380ba098a
=======
>>>>>>> d92383d42685fd4343d6a90af958558380ba098a
        this.cardText = cardText;
        this.listId = listId;
    }

    public Card() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardText() {
        return cardText;
    }

    public void setCardText(String cardText) {
        this.cardText = cardText;
    }

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardText='" + cardText + '\'' +
                ", listId=" + listId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (id != card.id) return false;
        if (listId != card.listId) return false;
        return cardText != null ? cardText.equals(card.cardText) : card.cardText == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (cardText != null ? cardText.hashCode() : 0);
        result = 31 * result + (int) (listId ^ (listId >>> 32));
        return result;
    }
}
