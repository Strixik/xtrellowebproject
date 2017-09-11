package lms.dao.repository;

import lms.dao.CRUD;
import lms.dao.DataSource;
import lms.dao.entity.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CardRepo implements CRUD<Card> {
    private static final Logger LOGGER = Logger.getLogger(CardRepo.class.getName());

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
            LOGGER.severe("Connection to database is lost:\t" + e.toString());
        }
    }
    @Override
    public void remove(long id) {
        DataSource dataSource = new DataSource();
        if (id > 0L) {
            try (Connection con = dataSource.getConnection();
                 PreparedStatement preparedSt =
                         con.prepareStatement("DELETE FROM card WHERE id =" + id)
            ) {
                preparedSt.executeUpdate();
            } catch (SQLException e) {
                LOGGER.severe("Connection to database is lost:\t" + e.toString());
            }
        }
    }

    @Override
    public List<Card> retrieveAll(long listId) {
        DataSource dataSource = new DataSource();
        List<Card> cards = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt =
                     con.prepareStatement("SELECT * FROM card WHERE id_list=\"" + listId + "\"");
             ResultSet rs = preparedSt.executeQuery()) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String cardText = rs.getString("card");
                Card card = new Card(id, cardText, listId);
                cards.add(card);
            }
        } catch (SQLException e) {
            LOGGER.severe("Connection to database is lost:\t" + e.toString());
        }
        return cards;
    }
    public void update(long id, String name, long idNote) {
        System.out.println(id + "-"+ name + "-"+ idNote);
        DataSource dataSource = new DataSource();
        if (id > 0L) {
            try (Connection con = dataSource.getConnection();
                 PreparedStatement preparedSt =
                         con.prepareStatement("UPDATE card SET card = ? WHERE id =\""+ idNote +"\" AND id_list =" + id)

            ) {
                preparedSt.setString(1, name);
                preparedSt.executeUpdate();
            } catch (SQLException e) {
                LOGGER.severe("Connection to database is lost: \t" + e.toString());
            }
        }
    }
}