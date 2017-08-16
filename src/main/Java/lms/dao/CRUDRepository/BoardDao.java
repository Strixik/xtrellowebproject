package lms.dao.CRUDRepository;

import lms.dao.entity.Board;

import java.util.List;

public interface BoardDao {
    List<Board> showAllBoards(long user_id);

    void saveBoard(Board board);

    void removeBoard(long id_board);

    //for admin
    List<Board> showAllBoards();
}
