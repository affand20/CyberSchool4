package com.trydev.affan.cyberschool;

/**
 * Created by affan on 06/11/17.
 */

public class Admin {

    private String nama;
    private String email;
    private String password;

    public Admin(){}

    public Admin(String nama, String email, String password) {
        this.nama = nama;
        this.email = email;
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
