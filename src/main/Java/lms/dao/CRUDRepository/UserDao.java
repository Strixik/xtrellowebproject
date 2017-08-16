package lms.dao.CRUDRepository;

import lms.dao.entity.User;

import java.util.List;

public interface UserDao {
    User findByUser(String login);

    void saveUser(User user);

    List<User> findByLoginByEmail(String searchString);

    //for admin
    List<User> showAllUsers();
}
