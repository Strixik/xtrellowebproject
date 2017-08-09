package lms.dao.entity;

public class User  {
    private long id;
    private String login;
    private String password;
    private String email;
    private String date_registered;
    private String sex;
    private String date_birth;
    private int block;
    private String firstname;
    private String secondname;
    private String contry;
    private String city;

    public User(long id, String login, String password, String email, String date_registered, String sex, String date_birth, int block, String firstname, String secondname, String contry, String city) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.date_registered = date_registered;
        this.sex = sex;
        this.date_birth = date_birth;
        this.block = block;
        this.firstname = firstname;
        this.secondname = secondname;
        this.contry = contry;
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

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getContry() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry = contry;
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
                ", firstname='" + firstname + '\'' +
                ", secondname='" + secondname + '\'' +
                ", contry='" + contry + '\'' +
                ", city='" + city + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (block != user.block) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (date_registered != null ? !date_registered.equals(user.date_registered) : user.date_registered != null)
            return false;
        if (sex != null ? !sex.equals(user.sex) : user.sex != null) return false;
        if (date_birth != null ? !date_birth.equals(user.date_birth) : user.date_birth != null) return false;
        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null) return false;
        if (secondname != null ? !secondname.equals(user.secondname) : user.secondname != null) return false;
        if (contry != null ? !contry.equals(user.contry) : user.contry != null) return false;
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
        result = 31 * result + block;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (secondname != null ? secondname.hashCode() : 0);
        result = 31 * result + (contry != null ? contry.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}
