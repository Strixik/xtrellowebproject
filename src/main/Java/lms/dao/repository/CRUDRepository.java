package lms.dao.repository;

import lms.dao.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface CRUDRepository<T> {
    void save(T toSave);
    void update(T toUpdate);
    void delete(T toDelete);
    void read(T toRead);


}
