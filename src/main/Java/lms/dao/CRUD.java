package lms.dao;

import java.util.List;

public interface CRUD<T> {

    void save(T someObject);

    void remove(long id);

    List<T> retrieveAll(long foreignKeyId);

}
