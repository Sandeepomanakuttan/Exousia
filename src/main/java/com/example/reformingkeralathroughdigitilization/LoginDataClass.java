package com.example.reformingkeralathroughdigitilization;

public class LoginDataClass {

    String Athority_Type, Authority_Place, userName, password, Name,user,id;

    public LoginDataClass() {
    }

    public LoginDataClass(String authority_type, String authority_Place, String userName, String password, String name,String user) {
        Athority_Type = authority_type;
        Authority_Place = authority_Place;
        this.userName = userName;
        this.password = password;
        Name = name;
        this.user=user;
    }

    public String getAthority_Type() {
        return Athority_Type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAuthority_Place() {
        return Authority_Place;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return Name;
    }

    public void setAthority_Type(String athority_Type) {
        Athority_Type = athority_Type;
    }

    public void setAuthority_Place(String authority_Place) {
        Authority_Place = authority_Place;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "LoginDataClass{" +
                "Athority_Type='" + Athority_Type + '\'' +
                ", Authority_place='" + Authority_Place + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}