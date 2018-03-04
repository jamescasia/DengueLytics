package xyz.aetherapps1.a8;

import java.util.Date;

public class UserInfo
{
    String name;
    String email;
    String admin;

    String age;
    String sex;
    String phone;
    String password;



    public UserInfo(String name, String email, String age, String sex, String phone  , String admin)
    {
        this.name = name;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.phone = phone;
        this.admin = admin;

    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
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


}