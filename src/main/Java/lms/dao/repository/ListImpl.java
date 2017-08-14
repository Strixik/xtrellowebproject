package lms.dao.repository;

import lms.dao.CRUDRepository.ListDao;
<<<<<<< HEAD
=======
import lms.dao.entity.Board;
>>>>>>> d92383d42685fd4343d6a90af958558380ba098a
import lms.dao.entity.Panel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListImpl implements ListDao {


    @Override
    public void saveList(Panel list) {
        DataSource dataSource = new DataSource();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstm = con.prepareStatement("INSERT INTO list (list, id_board) VALUES (?,?)")) {
            pstm.setString(1, list.getListName());
            pstm.setLong(2, list.getBoardId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeList(long id) {
        DataSource dataSource = new DataSource();
        if (id > 0L) {
            try (Connection con = dataSource.getConnection();
                 Statement stmt = con.createStatement();) {
                stmt.executeUpdate("DELETE FROM list WHERE id =" + id);
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }

        @Override
        public List<Panel> showAllLists (long boardId){
            DataSource dataSource = new DataSource();
            List<Panel> lists = new ArrayList<>();
            try (Connection con = dataSource.getConnection();
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM list WHERE id_board=\"" + boardId + "\"");) {
                while (rs.next()) {
                    long id = rs.getLong("id");
                    String list = rs.getString("list");
                    Panel alist = new Panel(
                            id,
                            list,
                            boardId
                    );
                    lists.add(alist);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return lists;
        }
}