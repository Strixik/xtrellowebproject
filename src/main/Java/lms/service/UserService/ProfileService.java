package lms.service.UserService;

import lms.dao.UserDao;
import lms.dao.entity.User;
import lms.dao.repository.UserRepo;
import lms.service.helpers.Helper;
import lms.views.HtmlViews.UserHtmlViews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import static lms.service.helpers.Helper.CHECK_EMAIL_ADDRESS_REGEX;

public class ProfileService {
    private static final Logger LOGGER = Logger.getLogger(ProfileService.class.getName());
    private PrintWriter out;

    public ProfileService(PrintWriter out) {
        this.out = out;
    }

    private UserDao userDao = new UserRepo();

    private String showProfileForm() {
        return UserHtmlViews.getInstance().getProfileForm();
    }

    public void showUserProfileForm(String login, HttpSession session) {
        User user = userDao.findUserByLogin(login);

        String profileForm = showProfileForm();

        profileForm = profileForm.replace("userFieldNumber1", user.getLogin());
        profileForm = profileForm.replace("userFieldNumber2", user.getPassword());
        profileForm = profileForm.replace("userFieldNumber3", user.getPassword());
        profileForm = profileForm.replace("userFieldNumber4", user.getEmail());
        profileForm = profileForm.replace("userFieldNumber5", String.valueOf(user.getId()));
        profileForm = profileForm.replace("userFieldNumber6", user.getDateOfRegistration());
        profileForm = profileForm.replace("userFieldNumber7", ((user.getSex()) == null)? "Стать": user.getSex());
        profileForm = profileForm.replace("userFieldNumber8", ((user.getDateOfBirth()) == null) ?
                "1900-01-01" : user.getDateOfBirth());

        if (session.getAttribute("status").equals("admin")) {
            profileForm = profileForm.replace("userFieldNumber9", user.getUserStatus());
            profileForm = profileForm.replace("readonly", "");
        }
        profileForm = profileForm.replace("userFieldNumber9", user.getUserStatus());
        profileForm = profileForm.replace("userFieldNumber0", ((user.getFirstName() == null) ?
                "Дані не заповнено" : user.getFirstName()));
        profileForm = profileForm.replace("userFieldNumber-1", ((user.getSecondName() == null) ?
                "Дані не заповнено" : user.getSecondName()));
        profileForm = profileForm.replace("userFieldNumber-2", ((user.getCountry() == null) ?
                "Дані не заповнено" : user.getCountry()));
        profileForm = profileForm.replace("userFieldNumber-3", ((user.getCity() == null) ?
                "Дані не заповнено" : user.getCity()));
        out.println(profileForm);
    }

    public boolean checkProfileForm(HttpServletRequest request) {
        String profileForm = showProfileForm();
        try {
            String updUserId = Helper.requestParameter("upuserId", request);
            profileForm = Helper.checkFormField(5, profileForm, updUserId, f -> {
                if (f.length() > 0) {
                    return null;
                }
                return "ID";
            });
            String updLogin = Helper.requestParameter("upLogin", request);
            profileForm = Helper.checkFormField(1, profileForm, updLogin, f -> {
                if (f.length() >= 3 && f.length() <= 20) {
                    return null;
                }
                return "Довжина логіну може бути від 3 до 20 символів";
            });
            String updFirstPassword = Helper.requestParameter("upFirstPassword", request);
            profileForm = Helper.checkFormField(2, profileForm, updFirstPassword, f -> {
                if (f.length() >= 6 && f.length() <= 20) {
                    return null;
                }
                return "Довжина паролю може бути від 6 до 20 символів";
            });
            String updSecondPassword = Helper.requestParameter("upSecondPassword", request);
            profileForm = Helper.checkFormField(3, profileForm, updSecondPassword, f -> {
                if (f.equals(updFirstPassword)) {
                    return null;
                }
                return "Паролі повинні співпадати";
            });
            String updEmail = Helper.requestParameter("upEmail", request);
            profileForm = Helper.checkFormField(4, profileForm, updEmail, f -> {
                if (f.matches(CHECK_EMAIL_ADDRESS_REGEX)) {
                    return null;
                }
                return "Email введено в неправильному форматі";
            });
            String updDateOfRegistration = Helper.requestParameter("update_registered", request);
            profileForm = Helper.checkFormField(6, profileForm, updDateOfRegistration, f -> {
                if (f.length() == 10) {
                    return null;
                }
                return "Неправильний формат дати";
            });
            String updSex = Helper.requestParameter("upsex", request);
            profileForm = Helper.checkFormField(7, profileForm, updSex, f -> {
                if (f.length() > 0) {
                    return null;
                }
                return "Виберіть Вашу стать";
            });
            String updDateOfBirth = Helper.requestParameter("update_birth", request);
            profileForm = Helper.checkFormField(8, profileForm, updDateOfBirth, f -> {
                if (f.length() == 10) {
                    return null;
                }
                return "Неправильний формат дати";
            });
            String updUserStatus = Helper.requestParameter("upblock", request);
            profileForm = Helper.checkFormField(9, profileForm, updUserStatus, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "По стандарту 0 опція не працює";
            });
            String updFirstName = Helper.requestParameter("upfirstname", request);
            profileForm = Helper.checkFormField(0, profileForm, updFirstName, f -> {
                if (f.length() > 1) {
                    return null;
                }
                return "Мінімальна довжина імені: 2 символи";
            });
            String updSecondName = Helper.requestParameter("upsecondname", request);
            profileForm = Helper.checkFormField(-1, profileForm, updSecondName, f -> {
                if (f.length() > 1) {
                    return null;
                }
                return "Мінімальна довжина прізвища 2 символів";
            });
            String updCountry = Helper.requestParameter("upcontry", request);
            profileForm = Helper.checkFormField(-2, profileForm, updCountry, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
            });
            String updCity = Helper.requestParameter("upcity", request);
            profileForm = Helper.checkFormField(-3, profileForm, updCity, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
            });

            if (!profileForm.contains("has-error")) {
                User user;
                user = new User(Long.parseLong(updUserId), updLogin, updSecondPassword, updEmail, updDateOfRegistration,
                        updSex, updDateOfBirth, updUserStatus, updFirstName, updSecondName, updCountry, updCity);
                userDao.saveUser(user);
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.warning("Some problem with profile form:\t" + e.toString());
        }
        out.println(profileForm);
        return false;
    }
}
