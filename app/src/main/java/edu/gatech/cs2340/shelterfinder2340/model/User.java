package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by Sylvia Li on 2018/2/12.
 *
 */

public class User {

    /**
     * Returns a user's attribute
     * @return The user's attribute
     */
    public String getAttribute() {
        return attribute;
    }


    /**
     * Sets a user's attribute to the passed in information
     * @param attribute The attribute that the user's attribute should be set to
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    private String attribute;

    /**
     * Returns a user's name
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    private String name;
    private String username;
    private String password;
    private String id;

    public User(){

    }
    /**
     * Constructor for a User
     * @param name the name of the user
     * @param username the username of the user
     * @param password the password of the user
     * @param id the id of the user
     */
    public User( String name, String username, String password, String id) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.id = id;
    }

    /**
     *  Constructor for a user that takes in only the username, id, and password
     * @param username the username of the user
     * @param password the password of the user
     * @param id the id of the user
     */
    public User(String username, String password, String id) {
        this("", username, password, id);

    }

    /**
     * Constructor for a user that takes in only a name and id
     * @param name the name of the user
     * @param id the id of the user
     */
    public User(String name, String id) {
        this(name, "","", id);

    }

    /**
     * Constructor for a user that takes in just an id
     * @param id the id of the user
     */
    public User(String id) {
        this.id = id;
        this.name = "";
        this.username = "";
        this.password = "";
    }

    /**
     * Returns the user's username
     * @return the username of a user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the user's password
     * @return the password of a user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user's id
     * @return the user's id
     */
    public String getId() { return id; }

    /**
     * Method to set the password of the user
     * @param passWord the password a user's password should be changed to
     */
    public void setPassword(String passWord) {
        this.password = passWord;
    }

    /**
     * Method to check if a user's username is equivalent to the username passed in
     * @param uName The name to check if it is the same as the user's actual username
     * @return if the user's real username is the same as the username passed in
     */
    public boolean checkUsername(String uName) {
        return (uName.equals(this.username));
    }

    /**
     * Method to check if a user's password is equivalent to the password passed in
     * @param pWord the password to check if it is the same as the user's actual password
     * @return if the user's real password is the same as the password passed in
     */
    public boolean checkPassword(String pWord) {
        return (pWord.equals(this.password));
    }

}
