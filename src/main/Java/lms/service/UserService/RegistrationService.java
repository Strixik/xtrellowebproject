package lms.service.UserService;

import lms.dao.UserDao;
import lms.dao.entity.User;
import lms.dao.repository.UserRepo;
import lms.service.helpers.Helper;
import lms.views.HtmlViews.UserHtmlViews;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.logging.Logger;

import static lms.service.helpers.Helper.CHECK_EMAIL_ADDRESS_REGEX;

public class RegistrationService {
    private static final Logger LOGGER = Logger.getLogger(RegistrationService.class.getName());
    private PrintWriter out;

    public RegistrationService(PrintWriter out) {
        this.out = out;
    }

    private UserDao userDao = new UserRepo();

    public void showRegistrationForm() {
        out.println(UserHtmlViews.getInstance().getRegistrationForm());
    }

    public boolean checkRegistrationForm(HttpServletRequest request) {
        String registrationForm = UserHtmlViews.getInstance().getRegistrationForm();
        try {
            String inputLogin = Helper.requestParameter("regLogin", request);
            registrationForm = Helper.checkFormField(1, registrationForm, inputLogin, f -> {
                User user = userDao.findUserByLogin(inputLogin);
                if (user == null) {
                    return null;
                }
                return "Такий користувач вже зареєстрований";
            });
            String inputFirstPassword = Helper.requestParameter("regFirstPasword", request);
            registrationForm = Helper.checkFormField(2, registrationForm, inputFirstPassword, f -> {
                if (f.length() >= 6 && f.length() <= 20) {
                    return null;
                }
                return "Довжина паролю може бути від 6 до 20 символів";
            });
            String inputSecondPassword = Helper.requestParameter("regSecondPassword", request);
            registrationForm = Helper.checkFormField(3, registrationForm, inputSecondPassword, f -> {
                if (f.equals(inputFirstPassword)) {
                    return null;
                }
                return "Паролі повинні співпадати!";
            });
            String inputEmail = Helper.requestParameter("regEmail", request);
            registrationForm = Helper.checkFormField(4, registrationForm, inputEmail, f -> {
                if (f.matches(CHECK_EMAIL_ADDRESS_REGEX)) {
                    return null;
                }
                return "Невірна ел. адреса!";
            });
            if (!registrationForm.contains("has-error")) {
                User user = new User(inputLogin, inputFirstPassword, inputEmail, LocalDate.now().toString());
                userDao.saveUser(user);
                return true;
            }

        } catch (UnsupportedEncodingException e) {
            LOGGER.warning("Some problem with registration form:\t" + e.toString());
        }
        out.println(registrationForm);
        return false;
    }

}
