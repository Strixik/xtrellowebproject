package lms.dao.entity;

public class Panel {
    private long id;
    private String panelTitle;
    private long boardId;

    public Panel(long id, String panelTitle, long boardId) {
        this.id = id;
        this.panelTitle = panelTitle;
        this.boardId = boardId;
    }

    public Panel(String panelTitle, long boardId) {
        this.id = 0L;
        this.panelTitle = panelTitle;
        this.boardId = boardId;
    }

    public Panel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    @Override
    public String toString() {
        return "Panel{" +
                "id=" + id +
                ", panelTitle='" + panelTitle + '\'' +
                ", boardId=" + boardId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        lms.dao.entity.Panel panel = (lms.dao.entity.Panel) o;

        if (id != panel.id) return false;
        if (boardId != panel.boardId) return false;
        return panelTitle != null ? panelTitle.equals(panel.panelTitle) : panel.panelTitle == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (panelTitle != null ? panelTitle.hashCode() : 0);
        result = 31 * result + (int) (boardId ^ (boardId >>> 32));
        return result;
    }
}


