package lms.dao.repository;

import lms.dao.CRUD;
import lms.dao.DataSource;
import lms.dao.entity.Board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BoardRepo implements CRUD<Board> {
    private static final Logger LOGGER = Logger.getLogger(BoardRepo.class.getName());

    @Override
    public void save(Board board) {
        DataSource dataSource = new DataSource();
        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt =
                     con.prepareStatement("INSERT INTO board (board, user_id) VALUES (?,?)")
        ) {
            preparedSt.setString(1, board.getBoardTitle());
            preparedSt.setLong(2, board.getUserId());
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
                 PreparedStatement preparedSt = con.prepareStatement("DELETE FROM board WHERE id =" + id)
            ) {
                preparedSt.executeUpdate();
            } catch (SQLException e) {
                LOGGER.severe("Connection to database is lost:\t" + e.toString());
            }
        }
    }

    @Override
    public List<Board> retrieveAll(long user_id) {
        DataSource dataSource = new DataSource();
        List<Board> boards = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt = con.prepareStatement("SELECT * FROM board WHERE user_id=\"" + user_id + "\"");
             ResultSet rs = preparedSt.executeQuery()) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String board = rs.getString("board");
                Board aboard = new Board(id, board, user_id
                );
                boards.add(aboard);
            }
        } catch (SQLException e) {
            LOGGER.severe("Connection to database is lost:\t" + e.toString());
        }
        return boards;
    }

    /**
     * method only for admin
     * @return List<Board> boards (all boards of all users)
     */

    public List<Board> retrieveAllBoardsForAdmin() {
        DataSource dataSource = new DataSource();
        List<Board> boards = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt = con.prepareStatement("SELECT * FROM board");
             ResultSet rs = preparedSt.executeQuery()
        ) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String boardTitle = rs.getString("board");
                Long usedId = rs.getLong("user_id");
                Board board = new Board(id, boardTitle, usedId);
                boards.add(board);
            }
        } catch (SQLException e) {
            LOGGER.severe("Connection to database is lost:\t" + e.toString());
        }
        return boards;
    }
    public List<Board> retrieveAllBoardsForSearch(String searchString, Long id) {
        DataSource dataSource = new DataSource();
        List<Board> boards = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt =
                     con.prepareStatement("SELECT DISTINCT id, board, user_id FROM board WHERE  board LIKE \"%" + searchString + "%\"AND user_id =\"" + id + "\"");
             ResultSet rs = preparedSt.executeQuery()
        ) {
            while (rs.next()) {
                Board board = new Board(
                        rs.getLong("id"),
                        rs.getString("board"),
                        rs.getLong("user_id")
                    );
                boards.add(board);
            }
            return boards;
        } catch (SQLException e) {
            LOGGER.severe("Connection to database is lost:\t" + e.toString());
        }
        return null;
    }
    }


