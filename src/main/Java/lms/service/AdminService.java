package lms.service;

import lms.dao.UserDao;
import lms.dao.entity.Board;
import lms.dao.entity.User;
import lms.dao.repository.BoardRepo;
import lms.dao.repository.UserRepo;
import lms.views.BoardHtmlViews;

import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

public class AdminService {
    private static Logger log = Logger.getLogger(UserService.class.getName());

    private PrintWriter out;

    public AdminService(PrintWriter out) {
        this.out = out;
    }

    public void showAllUsers() {
        UserDao userDao = new UserRepo();
        List<User> users = userDao.showAllUsers();
        out.println("<div class=\"row\">\n" +
                "    <div class=\"col-xs-12 col-sm-12 col-md-6 col-md-offset-3 col-lg-6 col-lg-offset-3\">\n" +
                "        <ul class=\"list-group\">");
        for (User u : users) {
            out.write("<a class=\"list-group-item\" href=\"/admintest?id=" + u.getId() + "&login=" + u.getLogin() + "\" ><span class=\"glyphicon glyphicon-user\" aria-hidden=\"true\"></span>" + "&nbsp;&nbsp;" + u.getLogin() + "&nbsp;&nbsp;" + "<span class=\"glyphicon glyphicon-envelope\" aria-hidden=\"true\"></span>" + "&nbsp;&nbsp;" + u.getEmail() + "&nbsp;&nbsp;" + "<span class=\"glyphicon glyphicon-plus pull-right\" aria-hidden=\"true\"></span></a>");
        }
        out.println("</ul>\n" +
                "    </div>\n" +
                "</div>");
    }

    /**
     * show all boards of all users
     */
    public void showAllBoardsForAdmin() {
        if (out == null) return;
        BoardRepo boardRepo = new BoardRepo();
        List<Board> boards = boardRepo.retrieveAllBoardsForAdmin();
        out.println(BoardHtmlViews.getInstance().getBoardAddModalWindow());
        UserDao userDao = new UserRepo();
        for (Board b : boards) {
            String boardTitle = BoardHtmlViews.getInstance().getBoardHtml();
            boardTitle = boardTitle.replace("<!--user-->",  userDao.findByUserId(b.getUserId()).getLogin() );
            boardTitle = boardTitle.replace("<!--board-->", b.getBoardTitle());
            boardTitle = boardTitle.replace("board_id", String.valueOf(b.getId()));
            out.println(boardTitle);
        }
    }
}
