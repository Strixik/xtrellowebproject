package lms.dao.CRUDRepository;

import lms.dao.entity.User;

import java.util.List;

public interface UserDao {
    User findByUser(String login);

    void saveUser(User user);

    public List<User> findByLoginByEmail(String searchString);
    List<User> allUser();
}
