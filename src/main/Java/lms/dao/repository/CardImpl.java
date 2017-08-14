package lms.dao.repository;

import lms.dao.CRUDRepository.CardDao;
import lms.dao.entity.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardImpl implements CardDao {

    @Override
    public void saveCard(Card card) {
        DataSource dataSource = new DataSource();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstm = con.prepareStatement("INSERT INTO card (card, id_list) VALUES (?,?)");) {
            pstm.setString(1, card.getCardText());
            pstm.setLong(2, card.getListId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCard(long id) {
        DataSource dataSource = new DataSource();
        if (id > 0L) {
            try (Connection con = dataSource.getConnection();
                 Statement stmt = con.createStatement();) {
                stmt.executeUpdate("DELETE FROM card WHERE id =" + id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

        @Override
        public List<Card> showAllCards (long listId){
            DataSource dataSource = new DataSource();
            List<Card> cards = new ArrayList<>();
            try (Connection con = dataSource.getConnection();
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM card WHERE id_list=\"" + listId + "\"");) {
                while (rs.next()) {
                    long id = rs.getLong("id");
                    String card = rs.getString("card");
                    Card acard = new Card(
                            id,
                            card,
                            listId
                    );
                    cards.add(acard);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return cards;
        }
}