package edu.gatech.cs2340.shelterfinder2340.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class EntityId {
    @Id
    private String className;
    private long counter =1L;

    public EntityId(){}

    public EntityId(String className) {
        this.className = className;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }
}
