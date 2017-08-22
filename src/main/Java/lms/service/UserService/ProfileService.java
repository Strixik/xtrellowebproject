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
        String profForm = showProfileForm();
        profForm = profForm.replace("xtrellovall1", "value=\"" + user.getLogin() + "\"");
        profForm = profForm.replace("xtrellovall2", "value=\"" + user.getPassword() + "\"");
        profForm = profForm.replace("xtrellovall3", "value=\"" + user.getPassword() + "\"");
        profForm = profForm.replace("xtrellovall4", "value=\"" + user.getEmail() + "\"");
        profForm = profForm.replace("xtrellovall5", "value=\"" + String.valueOf(user.getId()) + "\"");
        profForm = profForm.replace("xtrellovall6", "value=\"" + user.getDateOfRegistration() + "\"");
        profForm = profForm.replace("xtrellovall7", "value=\"" + user.getSex() + "\"");
        profForm = profForm.replace("xtrellovall8", "value=\"" + user.getDateOfBirth() + "\"");

        if (session.getAttribute("status").equals("admin")) {
            profForm = profForm.replace("xtrellovall9", "value=\"" + user.getUserStatus() + "\"");
        }
        profForm = profForm.replace("xtrellovall9", "value=\"" + user.getUserStatus() + "\"" + " readonly");
        profForm = profForm.replace("xtrellovall0", "value=\"" + ((user.getFirstName() == null) ?
                "Дані не заповнено" : user.getFirstName()) + "\"");
        profForm = profForm.replace("xtrellovall-1", "value=\"" + ((user.getSecondName() == null) ?
                "Дані не заповнено" : user.getSecondName()) + "\"");
        profForm = profForm.replace("xtrellovall-2", "value=\"" + ((user.getCountry() == null) ?
                "Дані не заповнено" : user.getCountry()) + "\"");
        profForm = profForm.replace("xtrellovall-3", "value=\"" + ((user.getCity() == null) ?
                "Дані не заповнено" : user.getCity()) + "\"");
        out.println(profForm);
    }

    public boolean checkProfileForm(HttpServletRequest request) {
        String profileForm = showProfileForm();
        try {
            String updUserId = Helper.requestParameter("upuserId", request);
            profileForm = Helper.checkFormField(5, profileForm, updUserId, f -> {
                if (f.length() > 0) {
                    return null;
                }
                return "Бла бла бла";
            });

            String updLogin = Helper.requestParameter("upLogin", request);
            profileForm = Helper.checkFormField(1, profileForm, updLogin, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
            });

            String updFirstPassword = Helper.requestParameter("upFirstPassword", request);
            profileForm = Helper.checkFormField(2, profileForm, updFirstPassword, f -> {
                if (f.length() >= 6) {
                    return null;
                }
                return "Мінімальна довжина 6 символів";
            });

            profileForm = Helper.checkFormField(2, profileForm, updFirstPassword, f -> {
                if (f.length() <= 20) {
                    return null;
                }
                return "Максимальна довжина 20 символів";
            });
            String updSecondPassword = Helper.requestParameter("upSecondPassword", request);

            profileForm = Helper.checkFormField(3, profileForm, updSecondPassword, f -> {
                if (f.equals(updFirstPassword)) {
                    return null;
                }
                return "Паролі повинні співпадати!";
            });
            String updEmail = Helper.requestParameter("upEmail", request);
            profileForm = Helper.checkFormField(4, profileForm, updEmail, f -> {
                if (f.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
                        ) {
                    return null;
                }
                return "Невірна ел. адреса!";
            });
            String updDateOfRegistration = Helper.requestParameter("update_registered", request);
            profileForm = Helper.checkFormField(6, profileForm, updDateOfRegistration, f -> {
                if (f.length() == 10) {
                    return null;
                }
                return "Довжина повина становити 10 символів";
            });

            String updSex = Helper.requestParameter("upsex", request);

            profileForm = Helper.checkFormField(7, profileForm, updSex, f -> {
                if (f.length() > 0) {
                    return null;
                }
                return "Виберіть Стать";
            });
            String updDateOfBirth = Helper.requestParameter("update_birth", request);

            profileForm = Helper.checkFormField(8, profileForm, updDateOfBirth, f -> {
                if (f.length() == 10) {
                    return null;
                }
                return "Довжина повина становити 10 символів";
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
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
            });

            String updSecondName = Helper.requestParameter("upsecondname", request);
            profileForm = Helper.checkFormField(-1, profileForm, updSecondName, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
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
