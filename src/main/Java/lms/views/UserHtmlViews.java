package lms.views;

/**
 * Lazy loading singleton with HTML parts for Note views
 */
public class UserHtmlViews {
    private static UserHtmlViews ourInstance;

    private final String topUserView;
    private final String formUserView;
    private final String bottomUserView;
    private final String logoutButton;
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
        this.topUserView = pathHtml.getPartialHtml("indextop.html");
        this.formUserView = pathHtml.getPartialHtml("form login.html");
        this.bottomUserView = pathHtml.getPartialHtml("indexbottom.html");
        this.logoutButton = pathHtml.getPartialHtml("start-logout-button.html");
        this.regForm = pathHtml.getPartialHtml("regform.html");
        this.profileForm = pathHtml.getPartialHtml("profileform.html");
    }

    public String getTopUserView() {
        return topUserView;
    }

    public String getFormUserView() {
        return formUserView;
    }

    public String getLogoutButton() {
        return logoutButton;
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
