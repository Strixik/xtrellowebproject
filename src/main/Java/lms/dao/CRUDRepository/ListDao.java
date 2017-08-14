package lms.dao.CRUDRepository;

import lms.dao.entity.Panel;

import java.util.List;

public interface ListDao {
    void saveList (Panel list);
<<<<<<< HEAD
    void removeList(long id);
    List<Panel> showAllLists(long listId);
=======
    void removeList (long id);
    List<Panel> showAllLists (long boardId);
>>>>>>> d92383d42685fd4343d6a90af958558380ba098a
}
