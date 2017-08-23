package lms.service;

import lms.dao.CRUD;
import lms.dao.entity.Board;
import lms.dao.repository.BoardRepo;
import lms.views.HtmlViews.BoardHtmlViews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;

public class BoardService {
    private static final Logger LOGGER = Logger.getLogger(BoardService.class.getName());
    private PrintWriter out;

    public BoardService(PrintWriter out) {
        this.out = out;
    }

    public boolean addBoardForm(HttpServletRequest request, HttpSession session) {
        try {
            String boardTitle = new String(request.getParameter("nameBoard").getBytes("UTF-8"));
            long userId = Long.parseLong(session.getAttribute("user_id").toString());
            LOGGER.info("User ID: " + userId + "Board title: " + boardTitle);
            if (userId != 0L && !boardTitle.isEmpty()) {
                Board board = new Board(boardTitle, userId);
                CRUD<Board> boardRepo = new BoardRepo();
                boardRepo.save(board);
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.severe("UnsupportedEncodingException:\t " + e.toString());
        }
        return false;
    }

    public boolean deleteBoard(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("boardid"));
        if (id > 0L) {
            CRUD<Board> boardRepo = new BoardRepo();
            boardRepo.remove(id);
            return true;
        } else return false;
    }

    public void showAllBoards(HttpSession session) {
        Long userId = Long.parseLong(session.getAttribute("user_id").toString());
        CRUD<Board> boardRepo = new BoardRepo();
        List<Board> boards = boardRepo.retrieveAll(userId);
        out.println(BoardHtmlViews.getInstance().getBoardAddModalWindow());
        for (Board b : boards) {
            String boardTitle = BoardHtmlViews.getInstance().getBoardHtml();
            boardTitle = boardTitle.replace("<!--board-->", b.getBoardTitle());
            boardTitle = boardTitle.replace("board_id", String.valueOf(b.getId()));
            out.println(boardTitle);
        }
    }

    /**
     * method is used for search requests
     *
     * @param request
     * @param session
     */
    public void showAllBoardsSearch(HttpServletRequest request, HttpSession session) {
        String textForSearch = request.getParameter("searchText");
        Long userId = Long.parseLong(session.getAttribute("user_id").toString());
        BoardRepo boardRepo = new BoardRepo();
        out.println(BoardHtmlViews.getInstance().getBoardAddModalWindow());

        boardRepo.retrieveAllBoardsForSearch(textForSearch, userId).stream().forEach(b -> {
            String boardTitle = BoardHtmlViews.getInstance().getBoardHtml();
            boardTitle = boardTitle.replace("<!--board-->", b.getBoardTitle());
            boardTitle = boardTitle.replace("board_id", String.valueOf(b.getId()));
            out.println(boardTitle);
        });
    }
}
