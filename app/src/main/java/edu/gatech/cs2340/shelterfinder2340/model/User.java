package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by Sylvia Li on 2018/2/12.
 *
 */

public class User {
    public String getUserName() {
        return userName;
    }

    private String userName;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    private String attribute;

    public String getName() {
        return name;
    }

    private String name;
    private String username;
    private String password;
    private String id;
    private Shelter reservedShelter;


    public User( String name, String username, String password, String id) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.id = id;
    }

    public User(String username, String password, String id) {
        this("", username, password, id);

    }

    public User(String name, String id) {
        this(name, "","", id);

    }
    public User(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassWord() {
        return password;
    }
    public String getId() { return id; }
    public void setPassword(String passWord) {
        this.password = passWord;
    }
    public boolean checkUsername(String uName) {
        return (uName.equals(this.username));
    }
    public boolean checkPassword(String pWord) {
        return (pWord.equals(this.password));
    }
    public boolean hasReservedShelter(){return !(reservedShelter == null);}
}
