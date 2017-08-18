package lms.views;

public class BoardHtmlViews {
    private static BoardHtmlViews ourInstance;

    private final String modalButton;
    private final String boardHtml;


    public static BoardHtmlViews getInstance() {
        if (ourInstance == null) {
            ourInstance = new BoardHtmlViews();
        }
        return ourInstance;
    }


    public BoardHtmlViews() {
        PathHtml pathHtml = PathHtml.getInstance();

        this.modalButton = pathHtml.getPartialHtml("board-modal-button.html");
        this.boardHtml = pathHtml.getPartialHtml("board.html");
    }

    public String getModal_boottom() {
        return modalButton;
    }

    public String getBoardHtml() {
        return boardHtml;
    }
}
