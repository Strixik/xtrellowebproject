package lms.dao.repository;

import lms.dao.CRUD;
import lms.dao.DataSource;
import lms.dao.entity.Panel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PanelRepo implements CRUD<Panel> {
    private static final Logger LOGGER = Logger.getLogger(PanelRepo.class.getName());

    @Override
    public void save(Panel panel) {
        DataSource dataSource = new DataSource();
        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt =
                     con.prepareStatement("INSERT INTO list (list, id_board) VALUES (?,?)")
        ) {
            preparedSt.setString(1, panel.getPanelTitle());
            preparedSt.setLong(2, panel.getBoardId());
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
                         con.prepareStatement("DELETE FROM list WHERE id =" + id)
            ) {
                preparedSt.executeUpdate();
            } catch (SQLException e) {
                LOGGER.severe("Connection to database is lost: \t" + e.toString());
            }
        }
    }

    @Override
    public List<Panel> retrieveAll(long boardId) {
        DataSource dataSource = new DataSource();
        List<Panel> panels = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt =
                     con.prepareStatement("SELECT * FROM list WHERE id_board=\"" + boardId + "\"");
             ResultSet rs = preparedSt.executeQuery()
        ) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String panelName = rs.getString("list");
                Panel panel = new Panel(id, panelName, boardId);
                panels.add(panel);
            }
        } catch (SQLException e) {
            LOGGER.severe("Connection to database is lost:\t" + e.toString());
        }
        return panels;
    }
}