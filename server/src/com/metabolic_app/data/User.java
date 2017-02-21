package com.metabolic_app.data;

/**
 * Created by Kiran Gopinathan on 14/02/17.
 *
 * Represents a user on the system.
 * A type variable is included to differentiate between patients and clinicians
 *
 */
public abstract class User {

    private String username;
    private String password;
    private long id;


    public long getId() {
        return id;
    }

    public void setId(long id) {this.id = id;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
