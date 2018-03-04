package xyz.aetherapps1.a8;
public class UserInfo
{
    String name;
    String email;
    String age;
    String sex;
    String phone;
    String password;
    boolean agree;

    public UserInfo(String name, String email, String age, String sex, String phone, String password, boolean agree)
    {
        this.name = name;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.phone = phone;
        this.password = password;
        this.agree = agree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }
}