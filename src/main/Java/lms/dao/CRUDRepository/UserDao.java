package lms.dao.CRUDRepository;

import lms.dao.entity.User;

public interface UserDao {
    User findByUser(String login);

    void saveUser(User user);
}
