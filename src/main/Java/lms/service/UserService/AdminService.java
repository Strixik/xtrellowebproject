package lms.service.UserService;

import lms.dao.UserDao;
import lms.dao.repository.BoardRepo;
import lms.dao.repository.UserRepo;
import lms.views.BoardHtmlViews;
import lms.views.UserHtmlViews;

import java.io.PrintWriter;
import java.util.logging.Logger;

public class AdminService {
    private static final Logger LOGGER = Logger.getLogger(AdminService.class.getName());

    private PrintWriter out;

    public AdminService(PrintWriter out) {
        this.out = out;
    }

    public String showUserForm() {
        return UserHtmlViews.getInstance().getAdminUserForm();
    }

    public String showUserInsertForm() {
        return UserHtmlViews.getInstance().getAdminInsertForm();
    }

    UserDao userDao = new UserRepo();

    public void showAllUsers() {

        userDao.showAllUsers().stream().forEach(u -> {
            String showUserForm = showUserForm();
            String showUserInsertForm = showUserInsertForm();
            showUserInsertForm = showUserInsertForm.replace("getId", String.valueOf(u.getId()));
            showUserInsertForm = showUserInsertForm.replace("getLogin", u.getLogin());
            showUserInsertForm = showUserInsertForm.replace("getEmail", u.getEmail());
            showUserForm = showUserForm.replace("<!--insert-->", showUserInsertForm);
            out.write(showUserForm);
        });

    }

    /**
     * show all boards of all users
     */
    public void showAllBoardsForAdmin() {

        BoardRepo boardRepo = new BoardRepo();
        out.println(BoardHtmlViews.getInstance().getBoardAddModalWindow());
        boardRepo.retrieveAllBoardsForAdmin().stream().forEach(b -> {
            String boardTitle = BoardHtmlViews.getInstance().getBoardHtml();
            boardTitle = boardTitle.replace("<!--user-->", userDao.findUserById(b.getUserId()).getLogin());
            boardTitle = boardTitle.replace("<!--board-->", b.getBoardTitle());
            boardTitle = boardTitle.replace("board_id", String.valueOf(b.getId()));
            out.println(boardTitle);
        });
    }
}
