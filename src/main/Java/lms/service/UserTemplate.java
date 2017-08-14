package lms.service;

import lms.dao.CRUDRepository.UserDao;
import lms.dao.entity.User;
import lms.dao.repository.UserImpl;
import lms.views.UserHtmlViews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;

public class UserTemplate {

    interface FormField<E> {
        String check(E e);
    }

    private PrintWriter out;

    public UserTemplate(PrintWriter out) {
        this.out = out;
    }

    public void showLoginForm() {
        if (out == null) return;
        out.println(UserHtmlViews.getInstance().getFormUserView());
    }

    public void showRegisterForm() {
        if (out == null) return;
        out.println(UserHtmlViews.getInstance().getRegForm());
    }

    public void showProfileForm() {
        if (out == null) return;
        out.println(UserHtmlViews.getInstance().getProfileForm());
    }

    public boolean checkLogin(HttpServletRequest request, HttpSession session) {
        if (out == null) return false;
        try {
            String login = new String(request.getParameter("login").getBytes("iso-8859-1"),
                    "UTF-8");
            String password = new String(request.getParameter("password").getBytes("iso-8859-1"),
                    "UTF-8");
            UserDao userDao = new UserImpl();
            User user = userDao.findByUser(login);
            if (user == null ) {
                showErrorLoginForm();
                return false;
            } else if (user.getBlock()== 1) {
                showBlockLoginForm();
                return false;
            } else if (user.getPassword().equals(password)) {
                session.setAttribute("user_id", user.getId());
                session.setAttribute("login", user.getLogin());
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        showErrorLoginForm();
        return false;

    }

    private void showErrorLoginForm() {
        String loginForm = UserHtmlViews.getInstance().getFormUserView();
        loginForm = loginForm.replace("<!--123-->", "<p class=\"text-danger text-center\">Логін або пароль не вірні!</p>");
        out.println(loginForm);
    }
    private void showBlockLoginForm() {
        String loginForm = UserHtmlViews.getInstance().getFormUserView();
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
                UserDao userDao = new UserImpl();
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
                UserDao userDao = new UserImpl();
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

    public void showUserProfileForm(String login) {
        if (out == null) return;
        UserDao userDao = new UserImpl();
        User user = userDao.findByUser(login);
        if (user == null) return;
        String regForm = UserHtmlViews.getInstance().getProfileForm();
        regForm = regForm.replace("xtrellovall1", "value=\"" + user.getLogin() + "\"");
        regForm = regForm.replace("xtrellovall2", "value=\"" + user.getPassword() + "\"");
        regForm = regForm.replace("xtrellovall3", "value=\"" + user.getPassword() + "\"");
        regForm = regForm.replace("xtrellovall4", "value=\"" + user.getEmail() + "\"");
        regForm = regForm.replace("xtrellovall5", "value=\"" + String.valueOf(user.getId()) + "\"");
        regForm = regForm.replace("xtrellovall6", "value=\"" + user.getDate_registered() + "\"");
        regForm = regForm.replace("xtrellovall7", "value=\"" + user.getSex() + "\"");
        regForm = regForm.replace("xtrellovall8", "value=\"" + user.getDate_birth() + "\"");
        regForm = regForm.replace("xtrellovall9", "value=\"" + user.getBlock() + "\"");
        regForm = regForm.replace("xtrellovall0", "value=\"" + user.getFirstname() + "\"");
        regForm = regForm.replace("xtrellovall-1", "value=\"" + user.getSecondname() + "\"");
        regForm = regForm.replace("xtrellovall-2", "value=\"" + user.getContry() + "\"");
        regForm = regForm.replace("xtrellovall-3", "value=\"" + user.getCity() + "\"");
        out.println(regForm);
    }

    public boolean checkProfileForm(HttpServletRequest request) {

        if (out == null) return false;
        String profForm = UserHtmlViews.getInstance().getProfileForm();
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
            String upFirstPasword = new String(request.getParameter("upFirstPasword").getBytes("iso-8859-1"),
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
                if (f.length() == 1) {
                    return null;
                }
                return "По стандарту 0 опція не працює";
            });

            String upfirstname = new String(request.getParameter("upfirstname").getBytes("iso-8859-1"),
                    "UTF-8");
            profForm = checkFormField(0, profForm, upfirstname, f -> {
                if (f.length() >= 5) {
                    return null;
                }
                return "Мінімальна довжина 5 символів";
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
                user = new User(Long.parseLong(upuserId), upLogin, upSecondPassword, upEmail, update_registered, upsex, update_birth, Integer.parseInt(upblock), upfirstname, upsecondname, upcontry, upcity);
                System.out.println("profile\t" + user);
                UserDao userDao = new UserImpl();
                userDao.saveUser(user);
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        out.println(profForm);
        return false;
    }
    public void showAllUsers(){
        UserDao userDao = new UserImpl();
       List<User> users = userDao.allUser();
       out.println("<div class=\"row\">\n" +
               "    <div class=\"col-xs-12 col-sm-12 col-md-6 col-md-offset-3 col-lg-6 col-lg-offset-3\">\n" +
               "        <ul class=\"list-group\">");
       for (User u : users){
           out.write("<a class=\"list-group-item\" href=\"/admintest?id="+u.getId()+"&login="+u.getLogin()+"\" ><span class=\"glyphicon glyphicon-user\" aria-hidden=\"true\"></span>"+"&nbsp;&nbsp;"+u.getLogin()+"&nbsp;&nbsp;"+"<span class=\"glyphicon glyphicon-envelope\" aria-hidden=\"true\"></span>"+"&nbsp;&nbsp;"+u.getEmail()+"&nbsp;&nbsp;"+"<span class=\"glyphicon glyphicon-plus pull-right\" aria-hidden=\"true\"></span></a>");
       }
        out.println("</ul>\n" +
                "    </div>\n" +
                "</div>");
    }


}
