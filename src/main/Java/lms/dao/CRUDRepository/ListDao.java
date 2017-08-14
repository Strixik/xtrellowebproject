package lms.dao.CRUDRepository;

import lms.dao.entity.Panel;

import java.util.List;

public interface ListDao {
    void saveList (Panel list);
    void removeList(long id);
    List<Panel> showAllLists(long listId);

}
