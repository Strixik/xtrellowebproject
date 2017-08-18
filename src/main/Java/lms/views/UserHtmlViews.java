package lms.views;

/**
 * Lazy loading singleton with HTML parts for Note views
 */
public class UserHtmlViews {
    private static UserHtmlViews ourInstance;

    private final String topUserView;
    private final String formUserView;
    private final String bottomUserView;
    private final String menuBarRight;
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
        this.topUserView = pathHtml.getPartialHtml("index-top.html");
        this.formUserView = pathHtml.getPartialHtml("login-form.html");
        this.bottomUserView = pathHtml.getPartialHtml("index-bottom.html");
        this.menuBarRight = pathHtml.getPartialHtml("menu-bar-right.html");
        this.regForm = pathHtml.getPartialHtml("reg-form.html");
        this.profileForm = pathHtml.getPartialHtml("profile-form.html");
    }

    public String getTopUserView() {
        return topUserView;
    }

    public String getFormUserView() {
        return formUserView;
    }

    public String getMenuBarRight() {
        return menuBarRight;
    }

    public String getBottomUserView() {
        return bottomUserView;
    }

    public String getRegForm() {
        return regForm;
    }

    public String getProfileForm() {
        return profileForm;
    }
}
