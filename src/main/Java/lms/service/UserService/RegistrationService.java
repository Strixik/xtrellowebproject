package lms.service.UserService;

import lms.dao.UserDao;
import lms.dao.entity.User;
import lms.dao.repository.UserRepo;
import lms.service.Helper;
import lms.views.UserHtmlViews;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.logging.Logger;

public class RegistrationService {
    private static final Logger LOGGER = Logger.getLogger(RegistrationService.class.getName());
    private PrintWriter out;

    public RegistrationService(PrintWriter out) {
        this.out = out;
    }

    UserDao userDao = new UserRepo();

    public void showRegisterForm() {
        if (out == null) return;
        out.println(UserHtmlViews.getInstance().getRegForm());
    }

    public boolean checkRegistrationForm(HttpServletRequest request) {
        String regForm = UserHtmlViews.getInstance().getRegForm();
        try {
            String regLogin = Helper.requestParameter("regLogin", request);
            regForm = Helper.checkFormField(1, regForm, regLogin, f -> {
                User user = userDao.findUserByLogin(regLogin);
                if (user == null) {
                    return null;
                }
                return "Такий користувач вже зареєстрований";
            });
            String regFirstPasword = Helper.requestParameter("regFirstPasword", request);
            regForm = Helper.checkFormField(2, regForm, regFirstPasword, f -> {
                if (f.length() >= 6) {
                    return null;
                }
                return "Мінімальна довжина 6 символів";
            });
            regForm = Helper.checkFormField(2, regForm, regFirstPasword, f -> {
                if (f.length() <= 20) {
                    return null;
                }
                return "Максимальна довжина 20 символів";
            });
            String regSecondPassword = Helper.requestParameter("regSecondPassword", request);

            regForm = Helper.checkFormField(3, regForm, regSecondPassword, f -> {
                if (f.equals(regFirstPasword)) {
                    return null;
                }
                return "Паролі повинні співпадати!";
            });
            String regEmail = Helper.requestParameter("regEmail", request);
            regForm = Helper.checkFormField(4, regForm, regEmail, f -> {
                if (f.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
                        ) {
                    return null;
                }
                return "Невірна ел. адреса!";
            });
            if (!regForm.contains("has-error")) {
                User user = new User(regLogin, regFirstPasword, regEmail, LocalDate.now().toString());
                userDao.saveUser(user);
                return true;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        out.println(regForm);
        return false;
    }

}
