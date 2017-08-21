package lms.dao;

import lms.dao.entity.User;

import java.util.List;

public interface UserDao {
    void saveUser(User user);

    User findByUser(String login);
    User findByUserId(long id);

    List<User> findByLoginByEmail(String searchString);

    //method only for admin
    List<User> showAllUsers();
}
