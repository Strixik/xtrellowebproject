package lms.service;

import lms.dao.UserDao;
import lms.dao.entity.User;
import lms.dao.repository.UserRepo;
import lms.views.UserHtmlViews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.logging.Logger;

public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    UserDao userDao = new UserRepo();

    private PrintWriter out;

    public UserService(PrintWriter out) {
        this.out = out;
    }

    public void showLoginForm() {
        if (out == null) return;
        out.println(UserHtmlViews.getInstance().getLoginForm());
    }

    public void showRegisterForm() {
        if (out == null) return;
        out.println(UserHtmlViews.getInstance().getRegForm());
    }

    public String showProfileForm() {
        String showProfileForm = UserHtmlViews.getInstance().getProfileForm();
        return showProfileForm;
    }

    public boolean checkLogin(HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
        if (out == null) return false;
        String login = Helper.requestParameter("login", request);
        String password = Helper.requestParameter("password", request);

        User user = userDao.findUserByLogin(login);
        if (showErrorLoginForm(user, password)) {
            session.setAttribute("user_id", user.getId());
            session.setAttribute("login", user.getLogin());
            session.setAttribute("status", user.getBlock());
            return true;
            }

        return false;
    }

    private boolean showErrorLoginForm(User user, String password) {
        String loginForm = UserHtmlViews.getInstance().getLoginForm();
        if (user == null || !user.getPassword().equals(password)) {
            loginForm = loginForm.replace("<!--#{ErrorLoginText}-->", "Логін або пароль не вірні!");
            out.println(loginForm);
            return false;
        } else if (user.getBlock().equals("block")) {
            loginForm = loginForm.replace("<!--#{ErrorLoginText}-->", "Даного користувача ЗАБЛОКОВАНО!");
            out.println(loginForm);
            return false;
        }

        return true;
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

    public void showUserProfileForm(String login, HttpSession session ) {
        User user = userDao.findUserByLogin(login);
        String profForm = showProfileForm();
        profForm = profForm.replace("xtrellovall1", "value=\"" + user.getLogin() + "\"");
        profForm = profForm.replace("xtrellovall2", "value=\"" + user.getPassword() + "\"");
        profForm = profForm.replace("xtrellovall3", "value=\"" + user.getPassword() + "\"");
        profForm = profForm.replace("xtrellovall4", "value=\"" + user.getEmail() + "\"");
        profForm = profForm.replace("xtrellovall5", "value=\"" + String.valueOf(user.getId()) + "\"");
        profForm = profForm.replace("xtrellovall6", "value=\"" + user.getDate_registered() + "\"");
        profForm = profForm.replace("xtrellovall7", "value=\"" + user.getSex() + "\"");
        profForm = profForm.replace("xtrellovall8", "value=\"" + user.getDate_birth() + "\"");
        if (session.getAttribute("status").equals("admin")){
            profForm = profForm.replace("xtrellovall9", "value=\"" + user.getBlock() + "\"");
        }
        profForm = profForm.replace("xtrellovall9", "value=\"" + user.getBlock() + "\"" + " readonly");
        profForm = profForm.replace("xtrellovall0", "value=\"" + ((user.getFirstName() == null) ? "Данні не заповнено" : user.getFirstName()) + "\"");
        profForm = profForm.replace("xtrellovall-1", "value=\"" + ((user.getSecondName() == null) ? "Данні не заповнено" : user.getSecondName()) + "\"");
        profForm = profForm.replace("xtrellovall-2", "value=\"" + ((user.getCountry() == null) ? "Данні не заповнено" : user.getCountry()) + "\"");
        profForm = profForm.replace("xtrellovall-3", "value=\"" + ((user.getCity() == null) ? "Данні не заповнено" : user.getCity()) + "\"");
        out.println(profForm);
    }

    public boolean checkProfileForm(HttpServletRequest request) {
        String profForm = showProfileForm();
        try {
            String upuserId = Helper.requestParameter("upuserId", request);
            profForm = Helper.checkFormField(5, profForm, upuserId, f -> {
                if (f.length() > 0) {
                    return null;
                }
                return "Бла бла бла";
            });

            String upLogin = Helper.requestParameter("upLogin", request);
            profForm = Helper.checkFormField(1, profForm, upLogin, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
            });

            String upFirstPasword = Helper.requestParameter("upFirstPassword", request);
            profForm = Helper.checkFormField(2, profForm, upFirstPasword, f -> {
                if (f.length() >= 6) {
                    return null;
                }
                return "Мінімальна довжина 6 символів";
            });

            profForm = Helper.checkFormField(2, profForm, upFirstPasword, f -> {
                if (f.length() <= 20) {
                    return null;
                }
                return "Максимальна довжина 20 символів";
            });
            String upSecondPassword = Helper.requestParameter("upSecondPassword", request);

            profForm = Helper.checkFormField(3, profForm, upSecondPassword, f -> {
                if (f.equals(upFirstPasword)) {
                    return null;
                }
                return "Паролі повинні співпадати!";
            });
            String upEmail = Helper.requestParameter("upEmail", request);
            profForm = Helper.checkFormField(4, profForm, upEmail, f -> {
                if (f.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
                        ) {
                    return null;
                }
                return "Невірна ел. адреса!";
            });
            String update_registered = Helper.requestParameter("update_registered", request);
            profForm = Helper.checkFormField(6, profForm, update_registered, f -> {
                if (f.length() == 10) {
                    return null;
                }
                return "Довжина повина становити 10 символів";
            });

            String upsex = Helper.requestParameter("upsex", request);

            profForm = Helper.checkFormField(7, profForm, upsex, f -> {
                if (f.length() >= 0) {
                    return null;
                }
                return "Виберіть Стать";
            });
            String update_birth = Helper.requestParameter("update_birth", request);

            profForm = Helper.checkFormField(8, profForm, update_birth, f -> {
                if (f.length() == 10) {
                    return null;
                }
                return "Довжина повина становити 10 символів";
            });
            String upblock = Helper.requestParameter("upblock", request);
            profForm = Helper.checkFormField(9, profForm, upblock, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "По стандарту 0 опція не працює";
            });

            String upfirstname = Helper.requestParameter("upfirstname", request);

            profForm = Helper.checkFormField(0, profForm, upfirstname, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
            });

            String upsecondname = Helper.requestParameter("upsecondname", request);
            profForm = Helper.checkFormField(-1, profForm, upsecondname, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
            });
            String upcontry = Helper.requestParameter("upcontry", request);
            profForm = Helper.checkFormField(-2, profForm, upcontry, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
            });
            String upcity = Helper.requestParameter("upcity", request);
            profForm = Helper.checkFormField(-3, profForm, upcity, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
            });

            if (!profForm.contains("has-error")) {
                User user;
                user = new User(Long.parseLong(upuserId), upLogin, upSecondPassword, upEmail, update_registered, upsex, update_birth, upblock, upfirstname, upsecondname, upcontry, upcity);
                userDao.saveUser(user);
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        out.println(profForm);
        return false;
    }

}
