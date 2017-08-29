package lms.dao.repository;

import lms.dao.DataSource;
import lms.dao.UserDao;
import lms.dao.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserRepo implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserRepo.class.getName());


    @Override
    public void saveUser(User user) {
        DataSource dataSource = new DataSource();

        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt = (user.getId() == 0L) ?
                     con.prepareStatement("INSERT INTO users (login, password, email, date_registered, sex, " +
                             "date_birth, block, firstname, secondname, contry, city)  VALUES (?,?,?,?,?,?,?,?,?,?,?)") :
                     con.prepareStatement("UPDATE users SET login=?, password=?, email=?, date_registered=?, sex=?," +
                             "date_birth=?, block=?, firstname=?, secondname=?, contry=?, city=? WHERE id=" + user.getId())
        ) {
            preparedSt.setString(1, user.getLogin());
            preparedSt.setString(2, user.getPassword());
            preparedSt.setString(3, user.getEmail());
            preparedSt.setString(4, user.getDateOfRegistration());
            preparedSt.setString(5, user.getSex());
            preparedSt.setString(6, user.getDateOfBirth());
            preparedSt.setString(7, (user.getUserStatus() == null) ? "user" : user.getUserStatus());
            preparedSt.setString(8, user.getFirstName());
            preparedSt.setString(9, user.getSecondName());
            preparedSt.setString(10, user.getCountry());
            preparedSt.setString(11, user.getCity());
            preparedSt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.severe("Connection to database is lost:\t" + e.toString());
        }
    }

    @Override
    public User findUserByLogin(String login) {
        DataSource dataSource = new DataSource();

        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt =
                     con.prepareStatement("SELECT * FROM users WHERE users.login=\"" + login + "\";");
             ResultSet rs = preparedSt.executeQuery()
        ) {
            if (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("date_registered"),
                        rs.getString("sex"),
                        rs.getString("date_birth"),
                        rs.getString("block"),
                        rs.getString("firstname"),
                        rs.getString("secondname"),
                        rs.getString("contry"),
                        rs.getString("city"));
                return user;
            }
        } catch (SQLException e) {
            LOGGER.severe("Connection to database is lost:\t" + e.toString());
        }
        return null;
    }

    @Override
    public User findUserById(long id) {
        DataSource dataSource = new DataSource();

        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt =
                     con.prepareStatement("SELECT * FROM users WHERE users.id=\"" + id + "\";");
             ResultSet rs = preparedSt.executeQuery()
        ) {
            if (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("date_registered"),
                        rs.getString("sex"),
                        rs.getString("date_birth"),
                        rs.getString("block"),
                        rs.getString("firstname"),
                        rs.getString("secondname"),
                        rs.getString("contry"),
                        rs.getString("city"));
                return user;
            }
        } catch (SQLException e) {
            LOGGER.severe("Connection to database is lost: \t" + e.toString());
        }
        return null;
    }


    @Override
    public List<User> findUserByLoginByEmail(String searchString) {

        DataSource dataSource = new DataSource();
        List<User> users = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt =
                     con.prepareStatement("SELECT DISTINCT id, login, password, email, date_registered FROM users " +
                     "WHERE (login LIKE \"%" + searchString + "%\" OR email LIKE \"%" + searchString + "%\") LIMIT 10");
             ResultSet rs = preparedSt.executeQuery()
        ) {
            while (rs.next()) {
                User user = new User(
                        rs.getLong("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("date_registered")
                );
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            LOGGER.severe("Connection to database is lost:\t" + e.toString());
        }
        return null;
    }

    /**
     * method only for admin
     * @return List<User> list of all xTrello users
     */
    @Override
    public List<User> showAllUsers() {
        DataSource dataSource = new DataSource();
        List<User> users = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedSt = con.prepareStatement("SELECT * FROM users");
             ResultSet rs = preparedSt.executeQuery()
        ) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("date_registered"),
                        rs.getString("sex"),
                        rs.getString("date_birth"),
                        rs.getString("block"),
                        rs.getString("firstname"),
                        rs.getString("secondname"),
                        rs.getString("contry"),
                        rs.getString("city"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            LOGGER.severe("Connection to database is lost: \t" + e.toString());
        }
        return null;
    }



}
