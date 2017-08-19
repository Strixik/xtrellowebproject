package lms.views;

/**
 * Lazy loading singleton with HTML parts
 */
public class UserHtmlViews {
    private static UserHtmlViews ourInstance;

    private final String indexTop;
    private final String indexBottom;
    private final String menuRight;
    private final String loginForm;
    private final String regForm;
    private final String profileForm;


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
        this.regForm = pathHtml.getPartialHtml("reg-form.html");
        this.profileForm = pathHtml.getPartialHtml("profile-form.html");
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

    public String getRegForm() {
        return regForm;
    }

    public String getProfileForm() {
        return profileForm;
    }
}
