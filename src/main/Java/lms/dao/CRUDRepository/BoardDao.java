package lms.dao.CRUDRepository;

import lms.dao.entity.Board;

import java.util.List;

public interface BoardDao {
    public List<Board> getAllBoard(long user_id);
    void saveBoard(Board board);
    void dellBoard(long id_board);
}
