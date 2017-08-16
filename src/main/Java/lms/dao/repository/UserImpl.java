package lms.dao.repository;

import lms.dao.CRUDRepository.UserDao;
import lms.dao.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserImpl implements UserDao {
    @Override
    public User findByUser(String login) {
        DataSource dataSource = new DataSource();

        try (Connection con = dataSource.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE users.login=\"" + login + "\";");
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
                        rs.getInt("block"),
                        rs.getString("firstname"),
                        rs.getString("secondname"),
                        rs.getString("contry"),
                        rs.getString("city"));
                //System.out.println(user);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveUser(User user) {
        DataSource dataSource = new DataSource();

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = (user.getId() == 0L) ?
                     con.prepareStatement("INSERT INTO users (login, password, email, date_registered, sex, date_birth, block, firstname, secondname, contry, city)  VALUES (?,?,?,?,?,?,?,?,?,?,?)") :
                     con.prepareStatement("UPDATE users SET login=?, password=?, email=?, date_registered=?, sex=?, date_birth=?, block=?, firstname=?, secondname=?, contry=?, city=? WHERE id=" + user.getId())
        ) {
            pstmt.setString(1, user.getLogin());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getDate_registered());
            pstmt.setString(5, user.getSex());
            pstmt.setString(6, user.getDate_birth());
            pstmt.setInt(7, user.getBlock());
            pstmt.setString(8, user.getFirstname());
            pstmt.setString(9, user.getSecondname());
            pstmt.setString(10, user.getContry());
            pstmt.setString(11, user.getCity());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> findByLoginByEmail(String searchString) {

        DataSource dataSource = new DataSource();
        List<User> users = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT id, login, password, email, date_registered FROM users WHERE (login LIKE \"%" + searchString + "%\" OR email LIKE \"%" + searchString + "%\") LIMIT 10");
        ){
            while(rs.next()) {
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
        }  catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> showAllUsers() {
        DataSource dataSource = new DataSource();
        List<User> users = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")
        ){
            while(rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("date_registered"),
                        rs.getString("sex"),
                        rs.getString("date_birth"),
                        rs.getInt("block"),
                        rs.getString("firstname"),
                        rs.getString("secondname"),
                        rs.getString("contry"),
                        rs.getString("city"));
                users.add(user);
            }
            return users;
        }  catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
