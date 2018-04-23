package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by Sylvia Li on 2018/4/23.
 */

public class UnbanUserCommand implements Command {
    HomelessPerson hp;
    UserDao dao;

    public UnbanUserCommand(HomelessPerson hp) {
        this.hp = hp;
        dao = new UserDao();
    }

    @Override
    public boolean execute() {
        dao.unsaveBannedUser(hp);
        return true;
    }

    @Override
    public boolean undo() {
        dao.saveBannedUser(hp);
        return true;
    }
}
