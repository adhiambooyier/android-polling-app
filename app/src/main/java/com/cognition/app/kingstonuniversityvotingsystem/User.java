package com.cognition.app.kingstonuniversityvotingsystem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Adhiambo Oyier on 4/8/2018.
 */

public class User {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("id_number")
    @Expose
    private String id_number;

    public User() {
    }

    public User(Integer id, String id_number, String name, String email, String password) {
        this.id = id;
        this.id_number = id_number;
        this.name = name;
        this.password = password;
        this.email = email;
    }
    public User(String id_number, String name, String email, String password) {
        this.id_number = id_number;
        this.name = name;
        this.password = password;
        this.email = email;
    }
    public String getId_number() {
        return id_number;
    }

    public void setId_number(Integer user_id) {
        this.id_number = id_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String user_name) {
        this.name = name;
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

    public void setEmail(Integer phone) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

