package lms.dao.repository;

import lms.dao.CRUDRepository.BoardDao;
import lms.dao.entity.Board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardImpl implements BoardDao {


    @Override
    public List<Board> getAllBoard(long user_id) {
        DataSource dataSource = new DataSource();
        List<Board> boards = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM board WHERE user_id=\"" + user_id + "\"");) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String board = rs.getString("board");
                Board aboard = new Board(
                        id,
                        board,
                        user_id
                );
                boards.add(aboard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boards;
    }

    @Override
    public List<Board> getAllBoard() {
        DataSource dataSource = new DataSource();
        List<Board> boards = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM board");) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String board = rs.getString("board");
                Long user_id = rs.getLong("user_id");
                Board aboard = new Board(
                        id,
                        board,
                        user_id
                );
                boards.add(aboard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boards;
    }


    @Override
    public void saveBoard(Board board) {
        DataSource dataSource = new DataSource();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstm = con.prepareStatement("INSERT INTO board (board, user_id) VALUES (?,?)");) {
            pstm.setString(1, board.getBoard_name());
            pstm.setLong(2, board.getUser_id());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dellBoard(long id) {
        DataSource dataSource = new DataSource();
        if (id > 0L) {
            try (Connection con = dataSource.getConnection();
                 Statement stmt = con.createStatement();) {
                stmt.executeUpdate("DELETE FROM board WHERE id =" + id);
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

    }
}
