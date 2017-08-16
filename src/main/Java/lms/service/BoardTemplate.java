package lms.service;

import lms.dao.CRUDRepository.BoardDao;
import lms.dao.entity.Board;
import lms.dao.repository.BoardImpl;
import lms.views.BoardHtmlViews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class BoardTemplate {

    private PrintWriter out;

    public BoardTemplate(PrintWriter out) {
        this.out = out;
    }

    public void showModalBottom() {
        if (out == null) return;
        out.println(BoardHtmlViews.getInstance().getModal_boottom());
    }

    public boolean addBoardForm(HttpServletRequest request, HttpSession session) {
        if (out == null) return false;
        try {
            long user_id = Long.parseLong(session.getAttribute("user_id").toString());
            String nameBoard = new String(request.getParameter("nameBoard").getBytes("iso-8859-1"),
                    "UTF-8");
            System.out.println("ІД Юзера + Імя дошки\n"+user_id + nameBoard);
            if (nameBoard != null && user_id != 0 && !nameBoard.isEmpty()) {
                Board board = new Board(nameBoard, user_id);
                BoardDao boardDao = new BoardImpl();
                boardDao.saveBoard(board);
                return true;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void showBoard(HttpSession session) {
        if (out == null) return;
        Long user_id = Long.parseLong(session.getAttribute("user_id").toString());
        BoardDao boardDao = new BoardImpl();
        List<Board> boards = boardDao.showAllBoards(user_id);
        out.println(BoardHtmlViews.getInstance().getModal_boottom());
        for (Board s : boards) {
            String board_name = BoardHtmlViews.getInstance().getBoard_name();
            board_name = board_name.replace("<!--board-->", s.getBoard_name());
            board_name = board_name.replace("board_id", String.valueOf(s.getId()));
            out.println(board_name);
        }
    }
    public void showBoardAll() {
        if (out == null) return;
        BoardDao boardDao = new BoardImpl();
        List<Board> boards = boardDao.showAllBoards();
        out.println(BoardHtmlViews.getInstance().getModal_boottom());
        for (Board s : boards) {
            String board_name = BoardHtmlViews.getInstance().getBoard_name();
            board_name = board_name.replace("<!--board-->", s.getBoard_name());
            board_name = board_name.replace("board_id", String.valueOf(s.getId()));
            out.println(board_name);
        }
    }

    public boolean dellBoards(HttpServletRequest request) {
        long board_id = Long.parseLong(request.getParameter("boardid").toString());
        if (board_id > 0L) {
            BoardDao boardDao = new BoardImpl();
            boardDao.removeBoard(board_id);
            return true;
        } else return false;
    }
}
