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

public class UserService {

    interface FormField<E> {
        String check(E e);
    }

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

    public boolean checkLogin(HttpServletRequest request, HttpSession session) {
        if (out == null) return false;
        try {
            String login = new String(request.getParameter("login").getBytes("iso-8859-1"),
                    "UTF-8");
            String password = new String(request.getParameter("password").getBytes("iso-8859-1"),
                    "UTF-8");
            UserDao userDao = new UserRepo();
            User user = userDao.findByUser(login);
            if (user == null) {
                showErrorLoginForm();
                return false;
            } else if (user.getBlock().equals("block")) {
                showBlockLoginForm();
                return false;
            } else if (user.getPassword().equals(password)) {
                session.setAttribute("user_id", user.getId());
                session.setAttribute("login", user.getLogin());
                session.setAttribute("status", user.getBlock());
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        showErrorLoginForm();
        return false;
    }

    private void showErrorLoginForm() {
        String loginForm = UserHtmlViews.getInstance().getLoginForm();
        loginForm = loginForm.replace("<!--123-->", "<p class=\"text-danger text-center\">Логін або пароль не вірні!</p>");
        out.println(loginForm);
    }
    private void showBlockLoginForm() {
        String loginForm = UserHtmlViews.getInstance().getLoginForm();
        loginForm = loginForm.replace("<!--123-->", "<p class=\"text-danger text-center\">Даного користувача ЗАБЛОКОВАНО!</p>");
        out.println(loginForm);
    }

    public boolean checkRegistrationForm(HttpServletRequest request) {
        if (out == null) return false;
        String regForm = UserHtmlViews.getInstance().getRegForm();
        try {
            String regLogin = new String(request.getParameter("regLogin").getBytes("iso-8859-1"),
                    "UTF-8");
            regForm = checkFormField(1, regForm, regLogin, f -> {
                UserDao userDao = new UserRepo();
                User user = userDao.findByUser(regLogin);
                if (user == null) {
                    return null;
                }
                return "Такий користувач вже зареєстрований";
            });
            String regFirstPasword = new String(request.getParameter("regFirstPasword").getBytes("iso-8859-1"),
                    "UTF-8");
            regForm = checkFormField(2, regForm, regFirstPasword, f -> {
                if (f.length() >= 6) {
                    return null;
                }
                return "Мінімальна довжина 6 символів";
            });
            regForm = checkFormField(2, regForm, regFirstPasword, f -> {
                if (f.length() <= 20) {
                    return null;
                }
                return "Максимальна довжина 20 символів";
            });
            String regSecondPassword = new String(request.getParameter("regSecondPassword").getBytes("iso-8859-1"),
                    "UTF-8");
            regForm = checkFormField(3, regForm, regSecondPassword, f -> {
                if (f.equals(regFirstPasword)) {
                    return null;
                }
                return "Паролі повинні співпадати!";
            });

            String regEmail = new String(request.getParameter("regEmail").getBytes("iso-8859-1"),
                    "UTF-8");
            regForm = checkFormField(4, regForm, regEmail, f -> {
                if (f.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
                        ) {
                    return null;
                }
                return "Невірна ел. адреса!";
            });
            if (!regForm.contains("has-error")) {
                User user;
                user = new User(regLogin, regFirstPasword, regEmail, LocalDate.now().toString());
                UserDao userDao = new UserRepo();
                userDao.saveUser(user);
                return true;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        out.println(regForm);
        return false;
    }

    private <E> String checkFormField(int fieldNumber, String formStr, E field, FormField<E> ff) {
        String msg = ff.check(field);
        formStr = formStr.replace("xtrellovall" + fieldNumber, "value=\"" + String.valueOf(field) + "\"");
        if (msg != null) {
            formStr = formStr.replace("group" + fieldNumber, "has-error");
            formStr = formStr.replace("<!--" + fieldNumber + "-->", msg);
        }
        return formStr;
    }

    public void showUserProfileForm(String login, HttpSession session ) {
        if (out == null) return;
        UserDao userDao = new UserRepo();
        User user = userDao.findByUser(login);
        if (user == null) return;
        String regForm = showProfileForm();
        regForm = regForm.replace("xtrellovall1", "value=\"" + user.getLogin() + "\"");
        regForm = regForm.replace("xtrellovall2", "value=\"" + user.getPassword() + "\"");
        regForm = regForm.replace("xtrellovall3", "value=\"" + user.getPassword() + "\"");
        regForm = regForm.replace("xtrellovall4", "value=\"" + user.getEmail() + "\"");
        regForm = regForm.replace("xtrellovall5", "value=\"" + String.valueOf(user.getId()) + "\"");
        regForm = regForm.replace("xtrellovall6", "value=\"" + user.getDate_registered() + "\"");
        regForm = regForm.replace("xtrellovall7", "value=\"" + user.getSex() + "\"");
        regForm = regForm.replace("xtrellovall8", "value=\"" + user.getDate_birth() + "\"");
        if (session.getAttribute("status").equals("admin")){
        regForm = regForm.replace("xtrellovall9", "value=\"" + user.getBlock() + "\"");
        }
        regForm = regForm.replace("xtrellovall9", "value=\"" + user.getBlock() + "\"" + " readonly");
        regForm = regForm.replace("xtrellovall0", "value=\"" + ((user.getFirstName() == null)?"Данні не заповнено":user.getFirstName())+ "\"");
        regForm = regForm.replace("xtrellovall-1", "value=\"" +((user.getSecondName() == null)?"Данні не заповнено":user.getSecondName())+ "\"");
        regForm = regForm.replace("xtrellovall-2", "value=\"" + ((user.getCountry() == null)?"Данні не заповнено":user.getCountry())+ "\"");
        regForm = regForm.replace("xtrellovall-3", "value=\"" +((user.getCity() == null)?"Данні не заповнено":user.getCity())+ "\"");
        out.println(regForm);
    }

    public boolean checkProfileForm(HttpServletRequest request) {

        if (out == null) return false;
        String profForm = showProfileForm();
        try {
            String upuserId = request.getParameter("upuserId");
            profForm = checkFormField(5, profForm, upuserId, f -> {
                if (f.length() > 0) {
                    return null;
                }
                return "Бла бла бла";
            });

            String upLogin = new String(request.getParameter("upLogin").getBytes("iso-8859-1"),
                    "UTF-8");
            profForm = checkFormField(1, profForm, upLogin, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
            });

            String upFirstPasword = new String(request.getParameter("upFirstPassword").getBytes("iso-8859-1"),
                    "UTF-8");
            profForm = checkFormField(2, profForm, upFirstPasword, f -> {
                if (f.length() >= 6) {
                    return null;
                }
                return "Мінімальна довжина 6 символів";
            });

            profForm = checkFormField(2, profForm, upFirstPasword, f -> {
                if (f.length() <= 20) {
                    return null;
                }
                return "Максимальна довжина 20 символів";
            });
            String upSecondPassword = new String(request.getParameter("upSecondPassword").getBytes("iso-8859-1"),
                    "UTF-8");

            profForm = checkFormField(3, profForm, upSecondPassword, f -> {
                if (f.equals(upFirstPasword)) {
                    return null;
                }
                return "Паролі повинні співпадати!";
            });
            String upEmail = new String(request.getParameter("upEmail").getBytes("iso-8859-1"),
                    "UTF-8");

            profForm = checkFormField(4, profForm, upEmail, f -> {
                if (f.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
                        ) {
                    return null;
                }
                return "Невірна ел. адреса!";
            });
            String update_registered = new String(request.getParameter("update_registered").getBytes("iso-8859-1"),
                    "UTF-8");

            profForm = checkFormField(6, profForm, update_registered, f -> {
                if (f.length() == 10) {
                    return null;
                }
                return "Довжина повина становити 10 символів";
            });


            String upsex = new String(request.getParameter("upsex").getBytes("iso-8859-1"),
                    "UTF-8");

            profForm = checkFormField(7, profForm, upsex, f -> {
                if (f.length() >= 0) {
                    return null;
                }
                return "Виберіть Стать";
            });
            String update_birth = new String(request.getParameter("update_birth").getBytes("iso-8859-1"),
                    "UTF-8");

            profForm = checkFormField(8, profForm, update_birth, f -> {
                if (f.length() == 10) {
                    return null;
                }
                return "Довжина повина становити 10 символів";
            });
            String upblock = new String(request.getParameter("upblock").getBytes("iso-8859-1"),
                    "UTF-8");

            profForm = checkFormField(9, profForm, upblock, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "По стандарту 0 опція не працює";
            });

            String upfirstname = new String(request.getParameter("upfirstname").getBytes("iso-8859-1"),
                    "UTF-8");

            profForm = checkFormField(0, profForm, upfirstname, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
            });

            String upsecondname = new String(request.getParameter("upsecondname").getBytes("iso-8859-1"),
                    "UTF-8");
            profForm = checkFormField(-1, profForm, upsecondname, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
            });
            String upcontry = new String(request.getParameter("upcontry").getBytes("iso-8859-1"),
                    "UTF-8");
            profForm = checkFormField(-2, profForm, upcontry, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
            });
            String upcity = new String(request.getParameter("upcity").getBytes("iso-8859-1"),
                    "UTF-8");
            profForm = checkFormField(-3, profForm, upcity, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Мінімальна довжина 3 символів";
            });

            if (!profForm.contains("has-error")) {
                User user;
                user = new User(Long.parseLong(upuserId), upLogin, upSecondPassword, upEmail, update_registered, upsex, update_birth, upblock, upfirstname, upsecondname, upcontry, upcity);
                UserDao userDao = new UserRepo();
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
