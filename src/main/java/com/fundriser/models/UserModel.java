package com.fundriser.models;


import lombok.Data;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
@Data

@Document(collection = "users")
public class UserModel {
    public String _id;
    public String username;
    public String email;
    public String usertype;
    public String password;

    public UserModel() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "_id='" + _id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", usertype='" + usertype + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
