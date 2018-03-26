package edu.gatech.cs2340.shelterfinder2340.model;

import android.widget.Adapter;

/**
 * Created by Sylvia Li on 2018/3/22.
 */

/**
 * public User(String userName, String passWord, String name) {
 this.userName = userName;
 this.passWord = passWord;
 this.name = name;
 }

 public User(String id) {
 this.id = id;
 }
 */
public class Admin extends User {
    public Admin(String userName, String passWord, String name) {
        super(userName, passWord, name);
        this.setAttribute("Admin");
    }
    public Admin(String id) {
        super(id);
        this.setAttribute("Admin");
    }
}
