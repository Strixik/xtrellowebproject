package lms.dao;

import lms.dao.entity.User;

import java.util.List;

public interface UserDao {
    void saveUser(User user);

    User findByUser(String login);

    List<User> findByLoginByEmail(String searchString);

    //for admin
    List<User> showAllUsers();
}
