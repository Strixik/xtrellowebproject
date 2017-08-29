package lms.views.HtmlViews;

import lms.views.PathHtml;

/**
 * Lazy loading singleton with HTML parts
 */
public class BoardHtmlViews {
    private static BoardHtmlViews ourInstance;

    private final String boardAddModalWindow;
    private final String boardHtml;


    public static BoardHtmlViews getInstance() {
        if (ourInstance == null) {
            ourInstance = new BoardHtmlViews();
        }
        return ourInstance;
    }


    private BoardHtmlViews() {
        PathHtml pathHtml = PathHtml.getInstance();

        this.boardAddModalWindow = pathHtml.getPartialHtml("board-add-modal-window.html");
        this.boardHtml = pathHtml.getPartialHtml("board.html");
    }

    public String getBoardAddModalWindow() {
        return boardAddModalWindow;
    }

    public String getBoardHtml() {
        return boardHtml;
    }
}
