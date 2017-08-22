package lms.views.HtmlViews;

import lms.views.PathHtml;

/**
 * Lazy loading singleton with HTML parts
 */
public class UserHtmlViews {
    private static UserHtmlViews ourInstance;

    private final String indexTop;
    private final String indexBottom;
    private final String menuRight;
    private final String loginForm;
    private final String registrationForm;
    private final String profileForm;
    private final String adminUserForm;
    private final String adminInsertForm;


    public static UserHtmlViews getInstance() {
        if (ourInstance == null) {
            ourInstance = new UserHtmlViews();
        }
        return ourInstance;
    }

    private UserHtmlViews() {
        PathHtml pathHtml = PathHtml.getInstance();
        this.indexTop = pathHtml.getPartialHtml("index-top.html");
        this.indexBottom = pathHtml.getPartialHtml("index-bottom.html");
        this.menuRight = pathHtml.getPartialHtml("menu-right.html");
        this.loginForm = pathHtml.getPartialHtml("login-form.html");
        this.registrationForm = pathHtml.getPartialHtml("registration-form.html");
        this.profileForm = pathHtml.getPartialHtml("profile-form.html");
        this.adminUserForm = pathHtml.getPartialHtml("user-list-for-admin.html");
        this.adminInsertForm = pathHtml.getPartialHtml("insert-for-admin-list-user.html");
    }

    public String getIndexTop() {
        return indexTop;
    }

    public String getIndexBottom() {
        return indexBottom;
    }

    public String getMenuRight() {
        return menuRight;
    }

    public String getLoginForm() {
        return loginForm;
    }

    public String getRegistrationForm() {
        return registrationForm;
    }

    public String getProfileForm() {
        return profileForm;
    }

    public String getAdminUserForm() {
        return adminUserForm;
    }

    public String getAdminInsertForm() {
        return adminInsertForm;
    }
}
