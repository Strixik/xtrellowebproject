package lms.dao.entity;

public class User {
    private long id;
    private String login;
    private String password;
    private String email;
    private String date_registered;
    private String sex;
    private String date_birth;
    private String block;
    private String firstName;
    private String secondName;
    private String country;
    private String city;

    public User(long id, String login, String password, String email, String date_registered, String sex, String date_birth, String block, String firstName, String secondName, String country, String city) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.date_registered = date_registered;
        this.sex = sex;
        this.date_birth = date_birth;
        this.block = block;
        this.firstName = firstName;
        this.secondName = secondName;
        this.country = country;
        this.city = city;
    }

    public User(long id, String login, String password, String email, String date_registered) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.date_registered = date_registered;
    }

    public User(String login, String password, String email, String date_registered) {
        this.id = 0L;
        this.login = login;
        this.password = password;
        this.email = email;
        this.date_registered = date_registered;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate_registered() {
        return date_registered;
    }

    public void setDate_registered(String date_registered) {
        this.date_registered = date_registered;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(String date_birth) {
        this.date_birth = date_birth;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", date_registered='" + date_registered + '\'' +
                ", sex='" + sex + '\'' +
                ", date_birth='" + date_birth + '\'' +
                ", block=" + block +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (date_registered != null ? !date_registered.equals(user.date_registered) : user.date_registered != null)
            return false;
        if (sex != null ? !sex.equals(user.sex) : user.sex != null) return false;
        if (date_birth != null ? !date_birth.equals(user.date_birth) : user.date_birth != null) return false;
        if (block != null ? !block.equals(user.block) : user.block != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (secondName != null ? !secondName.equals(user.secondName) : user.secondName != null) return false;
        if (country != null ? !country.equals(user.country) : user.country != null) return false;
        return city != null ? city.equals(user.city) : user.city == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (date_registered != null ? date_registered.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (date_birth != null ? date_birth.hashCode() : 0);
        result = 31 * result + (block != null ? block.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}
