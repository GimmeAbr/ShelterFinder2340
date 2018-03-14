package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by Sylvia Li on 2018/2/12.
 *
 */

public class User {
    private String name;
    private String username;
    private String password;
    private long id;
    private Shelter reservedShelter;


    public User( String name, String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.id = id;
    }

    public User(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.name = name;

    }

    public User(long id) {
        this.id = id;

    }

    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }
    public String getPassWord() {
        return password;
    }
    public long getId() { return id;}
    public void setPassWord(String passWord) {
        this.password = passWord;
    }
    public boolean checkUserName(String uName) {
        return (uName.equals(this.username));
    }
    public boolean checkPassWord(String pWord) {
        return (pWord.equals(this.password));
    }
    public boolean hasReservedShelter(){return !(reservedShelter == null);}
}
