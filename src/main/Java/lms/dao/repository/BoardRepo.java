package lms.dao.repository;

import lms.dao.CRUD;
import lms.dao.DataSource;
import lms.dao.entity.Board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardRepo implements CRUD<Board> {
    @Override
    public void save(Board board) {
        DataSource dataSource = new DataSource();
        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt = con.prepareStatement("INSERT INTO board (board, user_id) VALUES (?,?)")
        ) {
            preparedSt.setString(1, board.getBoard_name());
            preparedSt.setLong(2, board.getUser_id());
            preparedSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(long id) {
        DataSource dataSource = new DataSource();
        if (id > 0L) {
            try (Connection con = dataSource.getConnection();
                 Statement statement = con.createStatement()
            ) {
                statement.executeUpdate("DELETE FROM board WHERE id =" + id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Board> retrieveAll(long user_id) {
        DataSource dataSource = new DataSource();
        List<Board> boards = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM board WHERE user_id=\"" + user_id + "\"");) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String board = rs.getString("board");
                Board aboard = new Board(id, board, user_id
                );
                boards.add(aboard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boards;
    }

    /**
     * method for admin
     *
     * @return List<Board> boards (all boards of all users)
     */

    public List<Board> retrieveAllBoardsForAdmin() {
        DataSource dataSource = new DataSource();
        List<Board> boards = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM board")
        ) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String board = rs.getString("board");
                Long user_id = rs.getLong("user_id");
                Board aboard = new Board(id, board, user_id
                );
                boards.add(aboard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boards;
    }
}
