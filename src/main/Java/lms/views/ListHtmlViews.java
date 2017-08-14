package lms.views;

public class ListHtmlViews {
    private static ListHtmlViews ourInstance;

    private final String modalBottom;
    private final String listName;


    public static ListHtmlViews getInstance() {
        if (ourInstance == null) {
            ourInstance = new ListHtmlViews();
        }
        return ourInstance;
    }


    public ListHtmlViews() {
        PathHtml pathHtml = PathHtml.getInstance();

        this.modalBottom = pathHtml.getPartialHtml("modallistbottom.html");
        this.listName = pathHtml.getPartialHtml("listname.html");
    }

    public String getModalBottom() {
        return modalBottom;
    }

    public String getListName() {
        return listName;
    }
}
