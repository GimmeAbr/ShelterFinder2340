package edu.gatech.cs2340.shelterfinder2340.model;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.Collections;

// One MongoClient is created for all connections to the database
// Created a factory method so all daos would use the same mongoClient

public class MongoFactory {

    private Morphia morphiaDriver;
    private Datastore datastoreDriver;

    private String classToMapClassPath;

    private static MongoClient client;
    private static MongoDatabase databaseDriver;

    // The following parameters should be loaded from the configuration file based off what deployment
    private static String host;
    private static int port;

    private static String authenticationDB;
    private static String user;
    private static String password;

    private static String databaseName;

    // Create the MongoFactory
    // If a client is already made, don't make a new one - One MongoClient/JVM
    public MongoFactory(String pHost, int pPort, String pAuthenticationDB, String pUser, String pPassword, String pDatabaseName, String classToMapClassPath){
        if (client == null) {
            if (pHost.equals("")) {
                pHost = "localhost";
                pPort = 27017;
                client = new MongoClient();
            } else {
                if (pAuthenticationDB.equals("") || pUser.equals("") || pPassword.equals("")) {
                    client = new MongoClient(pHost, pPort);
                } else {
                    client = new MongoClient(
                            new ServerAddress(pHost, pPort),
                            Collections.singletonList(
                                    MongoCredential.createCredential(
                                            pUser,
                                            pAuthenticationDB,
                                            pPassword.toCharArray()
                                    )
                            )
                    );
                }
            }
            host = pHost;
            port = pPort;
            authenticationDB = pAuthenticationDB;
            user = pUser;
            databaseName = pDatabaseName;
        }
        this.classToMapClassPath = classToMapClassPath;
        databaseDriver = client.getDatabase(databaseName);
        boolean ignoreInvalidClasses = true;
        this.morphiaDriver = new Morphia();
        this.morphiaDriver.mapPackage(classToMapClassPath, ignoreInvalidClasses);
        this.datastoreDriver = morphiaDriver.createDatastore(client, databaseName);
    }
    public MongoFactory(String mongodbUri){
        if (client == null) {
            client = new MongoClient(mongodbUri);
        }

    }

    public Morphia getMorphiaDriver() {
        return morphiaDriver;
    }
    public MongoClient getClient() {
        return client;
    }
    public MongoDatabase getDatabaseDriver() {
        return databaseDriver;
    }
    public Datastore getDatastoreDriver() {
        return datastoreDriver;
    }
}