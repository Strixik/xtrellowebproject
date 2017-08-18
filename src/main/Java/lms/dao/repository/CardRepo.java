package lms.dao.repository;

import lms.dao.CRUD;
import lms.dao.DataSource;
import lms.dao.entity.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CardRepo implements CRUD<Card> {
    private static Logger log = Logger.getLogger(CardRepo.class.getName());

    @Override
    public void save(Card card) {
        DataSource dataSource = new DataSource();
        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt =
                     con.prepareStatement("INSERT INTO card (card, id_list) VALUES (?,?)")
        ) {
            preparedSt.setString(1, card.getCardText());
            preparedSt.setLong(2, card.getListId());
            preparedSt.executeUpdate();
        } catch (SQLException e) {
            log.severe("Connection to database is lost: \t" + e.toString());
        }
    }
    @Override
    public void remove(long id) {
        DataSource dataSource = new DataSource();
        if (id > 0L) {
            try (Connection con = dataSource.getConnection();
                 Statement statement = con.createStatement()
            ) {
                statement.executeUpdate("DELETE FROM card WHERE id =" + id);
            } catch (SQLException e) {
                log.severe("Connection to database is lost: \t" + e.toString());
            }
        }
    }

    @Override
    public List<Card> retrieveAll(long listId) {
        DataSource dataSource = new DataSource();
        List<Card> cards = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet rs =
                     statement.executeQuery("SELECT * FROM card WHERE id_list=\"" + listId + "\"")
        ) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String cardText = rs.getString("card");
                Card acard = new Card(id, cardText, listId);
                cards.add(acard);
            }
        } catch (SQLException e) {
            log.severe("Connection to database is lost: \t" + e.toString());
        }
        return cards;
    }
}