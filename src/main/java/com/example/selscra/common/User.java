package com.example.selscra.common;

public class User {

    private int id;
    private String email;
    private String password;
    private String last_logged;

    public User(int id, String email, String password, String last_logged) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.last_logged = last_logged;
    }

    public User(String email, String password, String last_logged) {
        this.email = email;
        this.password = password;
        this.last_logged = last_logged;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLast_logged() {
        return last_logged;
    }

    public void setLast_logged(String last_logged) {
        this.last_logged = last_logged;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", last_loged=" + last_logged +
                '}';
    }
}
