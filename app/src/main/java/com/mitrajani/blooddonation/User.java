package com.mitrajani.blooddonation;

/**
 * Created by Mit Rajani on 12/1/2018.
 */

public class User {

    private String username, email, fname, lname, DOB;
    private int id;

    public User (int id, String username, String email, String fname, String lname, String DOB)
    {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.DOB = DOB;
    }

    public int getId(){
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public String getFname()
    {
        return fname;
    }

    public String getLname() {return lname; }

    public String getDOB() {return DOB; }
}
