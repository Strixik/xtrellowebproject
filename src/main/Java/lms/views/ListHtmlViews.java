package lms.views;

public class ListHtmlViews {
    private static ListHtmlViews ourInstance;

    private final String modalButton;
    private final String listHtml;


    public static ListHtmlViews getInstance() {
        if (ourInstance == null) {
            ourInstance = new ListHtmlViews();
        }
        return ourInstance;
    }


    public ListHtmlViews() {
        PathHtml pathHtml = PathHtml.getInstance();

        this.modalButton = pathHtml.getPartialHtml("list-modal-button.html");
        this.listHtml = pathHtml.getPartialHtml("list.html");
    }

    public String getModalButton() {
        return modalButton;
    }

    public String getListHtml() {
        return listHtml;
    }
}
