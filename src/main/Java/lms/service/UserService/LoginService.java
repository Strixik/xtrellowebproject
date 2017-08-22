package lms.service.UserService;

import lms.dao.UserDao;
import lms.dao.entity.User;
import lms.dao.repository.UserRepo;
import lms.service.Helper;
import lms.views.UserHtmlViews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

public class LoginService {
    private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());
    private PrintWriter out;

    public LoginService(PrintWriter out) {
        this.out = out;
    }

    UserDao userDao = new UserRepo();

    public void showLoginForm() {
        if (out == null) return;
        out.println(UserHtmlViews.getInstance().getLoginForm());
    }

    public boolean checkLogin(HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
        if (out == null) return false;
        String login = Helper.requestParameter("login", request);
        String password = Helper.requestParameter("password", request);

        User user = userDao.findUserByLogin(login);
        if (showLoginFailedMessage(user, password)) {
            session.setAttribute("user_id", user.getId());
            session.setAttribute("login", user.getLogin());
            session.setAttribute("status", user.getBlock());
            return true;
        }

        return false;
    }

    private boolean showLoginFailedMessage(User user, String password) {
        String loginForm = UserHtmlViews.getInstance().getLoginForm();
        if (user == null || !user.getPassword().equals(password)) {
            loginForm = loginForm.replace("<!--#{ErrorLoginText}-->", "Неправильний логін або пароль!");
            out.println(loginForm);
            return false;
        } else if (user.getBlock().equals("block")) {
            loginForm = loginForm.replace("<!--#{ErrorLoginText}-->", "Даного користувача ЗАБЛОКОВАНО!");
            out.println(loginForm);
            return false;
        }
        return true;
    }
}
