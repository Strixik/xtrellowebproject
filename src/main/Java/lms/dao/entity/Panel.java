package lms.dao.entity;

public class Panel {
        private long id;
        private String listName;
        private long boardId;

        public Panel(long id, String listName, long boardId) {
            this.id = id;
            this.listName = listName;
            this.boardId = boardId;
        }

        public Panel(String listName, long boardId) {
            this.id = 0L;
            this.listName = listName;
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

        public String getListName() {
            return listName;
        }

        public void setListName(String listName) {
            this.listName = listName;
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
                    ", listName='" + listName + '\'' +
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
            return listName != null ? listName.equals(panel.listName) : panel.listName == null;
        }

        @Override
        public int hashCode() {
            int result = (int) (id ^ (id >>> 32));
            result = 31 * result + (listName != null ? listName.hashCode() : 0);
            result = 31 * result + (int) (boardId ^ (boardId >>> 32));
            return result;
        }
    }


