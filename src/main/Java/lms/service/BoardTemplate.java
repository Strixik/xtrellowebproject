package lms.service;

import lms.dao.CRUD;
import lms.dao.entity.Board;
import lms.dao.repository.BoardRepo;
import lms.views.BoardHtmlViews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;

public class BoardTemplate {

    private PrintWriter out;

    public BoardTemplate(PrintWriter out) {
        this.out = out;
    }

    private static Logger logger = Logger.getGlobal();

    public void showModalBottom() {
        if (out == null) return;
        out.println(BoardHtmlViews.getInstance().getModal_boottom());
    }

    public boolean addBoardForm(HttpServletRequest request, HttpSession session) {
        if (out == null) return false;
        try {
            String boardTitle = new String(request.getParameter("nameBoard")
                    .getBytes("iso-8859-1"), "UTF-8");
            long userId = Long.parseLong(session.getAttribute("user_id").toString());
            logger.info("ІД Юзера + Імя дошки\n" + userId + boardTitle);
            if (userId != 0L && !boardTitle.isEmpty()) {
                Board board = new Board(boardTitle, userId);
                CRUD<Board> boardRepo = new BoardRepo();
                boardRepo.save(board);
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
        CRUD<Board> boardRepo = new BoardRepo();
        List<Board> boards = boardRepo.retrieveAll(user_id);
        out.println(BoardHtmlViews.getInstance().getModal_boottom());
        for (Board s : boards) {
            String boardTitle = BoardHtmlViews.getInstance().getBoardHtml();
            boardTitle = boardTitle.replace("<!--board-->", s.getBoard_name());
            boardTitle = boardTitle.replace("board_id", String.valueOf(s.getId()));
            out.println(boardTitle);
        }
    }

    public void showBoardAllForAdmin() {
        if (out == null) return;
        BoardRepo boardRepo = new BoardRepo();
        List<Board> boards = boardRepo.retrieveAllBoardsForAdmin();
        out.println(BoardHtmlViews.getInstance().getModal_boottom());
        for (Board s : boards) {
            String boardTitle = BoardHtmlViews.getInstance().getBoardHtml();
            boardTitle = boardTitle.replace("<!--board-->", s.getBoard_name());
            boardTitle = boardTitle.replace("board_id", String.valueOf(s.getId()));
            out.println(boardTitle);
        }
    }

    public boolean deleteBoard(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("boardid").toString());
        if (id > 0L) {
            CRUD<Board> boardRepo = new BoardRepo();
            boardRepo.remove(id);
            return true;
        } else return false;
    }
}
