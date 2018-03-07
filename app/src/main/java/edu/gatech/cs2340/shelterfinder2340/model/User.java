package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by Sylvia Li on 2018/2/12.
 *
 */

public abstract class User {
    public String getUserName() {
        return userName;
    }

    private String userName;

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    private String passWord;

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public boolean checkUserName(String uName) {
        return (uName.equals(this.userName));
    }

    public boolean checkPassWord(String pWord) {
        return (pWord.equals(this.passWord));
    }
}
