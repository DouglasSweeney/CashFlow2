package com.doug.cashflow.model.db;

/**
 * Created by Doug on 4/25/2017.
 */
public class User {
    private int _id;
    private String email;
    private String password;

    public User() {
    }

    public User(int _id, String email, String password) {
        this.setId(_id);
        this.setEmail(email);
        this.setPassword(password);
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
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

    @Override
    public String toString() {
        return "<Username: " + email + " Password: " + password + " UserId: " + _id + ">";
    }
}
