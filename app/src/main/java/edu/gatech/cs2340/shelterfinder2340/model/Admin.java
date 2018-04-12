package edu.gatech.cs2340.shelterfinder2340.model;

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
    /**
     * Constructor for Admin
     * @param userName the username of the admin
     * @param passWord the password of the admin
     * @param name the name of the admin
     */
    public Admin(String userName, String passWord, String name) {
        super(userName, passWord, name);
        this.setAttribute("Admin");
    }

    /**
     * Constructor for admin that takes in only the id
     * @param id the id of the admin
     */
    public Admin(String id) {
        super(id);
        this.setAttribute("Admin");
    }
}
