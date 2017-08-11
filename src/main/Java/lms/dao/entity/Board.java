package lms.dao.entity;

public class Board {
    private long id;
    private String board_name;
    private long user_id;

    public Board(long id, String board_name, long user_id) {
        this.id = id;
        this.board_name = board_name;
        this.user_id = user_id;
    }

    public Board(String board_name, long user_id) {
        this.id = 0L;
        this.board_name = board_name;
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBoard_name() {
        return board_name;
    }

    public void setBoard_name(String board_name) {
        this.board_name = board_name;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }


    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", board_name='" + board_name + '\'' +
                ", user_id=" + user_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (id != board.id) return false;
        if (user_id != board.user_id) return false;
        return board_name != null ? board_name.equals(board.board_name) : board.board_name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (board_name != null ? board_name.hashCode() : 0);
        result = 31 * result + (int) (user_id ^ (user_id >>> 32));
        return result;
    }
}
