package lms.dao.repository;

import lms.dao.CRUD;
import lms.dao.DataSource;
import lms.dao.entity.Panel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PanelRepo implements CRUD<Panel> {
    private static Logger log = Logger.getLogger(PanelRepo.class.getName());

    @Override
    public void save(Panel panel) {
        DataSource dataSource = new DataSource();
        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt = con.prepareStatement("INSERT INTO list (list, id_board) VALUES (?,?)")
        ) {
            preparedSt.setString(1, panel.getListName());
            preparedSt.setLong(2, panel.getBoardId());
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
                 Statement stmt = con.createStatement()
            ) {
                stmt.executeUpdate("DELETE FROM list WHERE id =" + id);
            } catch (SQLException e) {
                log.severe("Connection to database is lost: \t" + e.toString());
            }
        }
    }


    @Override
    public List<Panel> retrieveAll(long boardId) {
        DataSource dataSource = new DataSource();
        List<Panel> panels = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM list WHERE id_board=\"" + boardId + "\"")
        ) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String panelName = rs.getString("list");
                Panel panel = new Panel(id, panelName, boardId);
                panels.add(panel);
            }
        } catch (SQLException e) {
            log.severe("Connection to database is lost: \t" + e.toString());
        }
        return panels;
    }
}