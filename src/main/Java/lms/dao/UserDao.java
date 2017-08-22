package lms.dao;

import lms.dao.entity.User;

import java.util.List;

public interface UserDao {
    void saveUser(User user);

    User findUserByLogin(String login);

    User findUserById(long id);

    List<User> findUserByLoginByEmail(String searchString);

    //method only for admin
    List<User> showAllUsers();
}
