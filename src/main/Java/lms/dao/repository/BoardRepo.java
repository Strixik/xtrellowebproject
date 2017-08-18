package lms.dao.repository;

import lms.dao.CRUD;
import lms.dao.DataSource;
import lms.dao.entity.Board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BoardRepo implements CRUD<Board> {
    private static Logger log = Logger.getLogger(BoardRepo.class.getName());

    @Override
    public void save(Board board) {
        DataSource dataSource = new DataSource();
        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt = con.prepareStatement("INSERT INTO board (board, user_id) VALUES (?,?)")
        ) {
            preparedSt.setString(1, board.getBoardTitle());
            preparedSt.setLong(2, board.getUserId());
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
                statement.executeUpdate("DELETE FROM board WHERE id =" + id);
            } catch (SQLException e) {
                log.severe("Connection to database is lost: \t" + e.toString());
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
            log.severe("Connection to database is lost: \t" + e.toString());
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
            log.severe("Connection to database is lost: \t" + e.toString());
        }
        return boards;
    }
}
