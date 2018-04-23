package edu.gatech.cs2340.shelterfinder2340.model;

import java.util.Stack;

/**
 * Created by Sylvia Li on 2018/4/23.
 */

public class CommandManager {
    private static final CommandManager cm = new CommandManager();
    private static Stack<Command> doneStack;
    private static Stack<Command> undoneStack;

    private CommandManager() {
        doneStack = new Stack<>();
        undoneStack = new Stack<>();
    }

    public static CommandManager getCm() {
        return cm;
    }

    public void banUser(HomelessPerson homelessPerson) {
        BanUserCommand banUserCommand = new BanUserCommand(homelessPerson);
        banUserCommand.execute();
        doneStack.add(banUserCommand);
    }

    public void unbanUser(HomelessPerson homelessPerson) {
        UnbanUserCommand unbanUserCommand = new UnbanUserCommand(homelessPerson);
        unbanUserCommand.execute();
        doneStack.add(unbanUserCommand);
    }

    public void undo() {
        Command undoCm = doneStack.pop();
        undoCm.undo();
        undoneStack.push(undoCm);
    }

    public void redo() {
        Command redoCm = undoneStack.pop();
        redoCm.execute();
        doneStack.push(redoCm);
    }
}
