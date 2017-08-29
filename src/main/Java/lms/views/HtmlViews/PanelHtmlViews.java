package lms.views.HtmlViews;

import lms.views.PathHtml;

/**
 * Lazy loading singleton with HTML parts
 */
public class PanelHtmlViews {
    private static PanelHtmlViews ourInstance;

    private final String listAddModalWindow;
    private final String listHtml;


    public static PanelHtmlViews getInstance() {
        if (ourInstance == null) {
            ourInstance = new PanelHtmlViews();
        }
        return ourInstance;
    }


    private PanelHtmlViews() {
        PathHtml pathHtml = PathHtml.getInstance();

        this.listAddModalWindow = pathHtml.getPartialHtml("panel-add-modal-window.html");
        this.listHtml = pathHtml.getPartialHtml("panel.html");
    }

    public String getListAddModalWindow() {
        return listAddModalWindow;
    }

    public String getListHtml() {
        return listHtml;
    }
}
