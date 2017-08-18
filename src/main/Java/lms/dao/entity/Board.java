package lms.dao.entity;

public class Board {
    private long id;
    private String boardTitle;
    private long userId;

    public Board(long id, String boardTitle, long userId) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.userId = userId;
    }

    public Board(String boardTitle, long userId) {
        this.id = 0L;
        this.boardTitle = boardTitle;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", boardTitle='" + boardTitle + '\'' +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (id != board.id) return false;
        if (userId != board.userId) return false;
        return boardTitle != null ? boardTitle.equals(board.boardTitle) : board.boardTitle == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (boardTitle != null ? boardTitle.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }
}
