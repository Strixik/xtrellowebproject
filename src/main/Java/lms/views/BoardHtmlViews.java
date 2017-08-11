package lms.views;

public class BoardHtmlViews {
    private static BoardHtmlViews ourInstance;

    private final String modal_bottom;
    private final String board_name;


    public static BoardHtmlViews getInstance() {
        if (ourInstance == null) {
            ourInstance = new BoardHtmlViews();
        }
        return ourInstance;
    }


    public BoardHtmlViews() {
        PathHtml pathHtml = PathHtml.getInstance();

        this.modal_bottom = pathHtml.getPartialHtml("modalboardbottom.html");
        this.board_name = pathHtml.getPartialHtml("boardname.html");
    }

    public String getModal_boottom() {
        return modal_bottom;
    }

    public String getBoard_name() {
        return board_name;
    }
}
