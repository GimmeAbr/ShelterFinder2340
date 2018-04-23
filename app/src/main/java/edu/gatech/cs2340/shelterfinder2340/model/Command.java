package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by Sylvia Li on 2018/4/23.
 */

public interface Command {
    boolean execute();

    boolean undo();
}
