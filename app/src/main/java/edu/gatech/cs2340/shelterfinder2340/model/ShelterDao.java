package edu.gatech.cs2340.shelterfinder2340.model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mongodb.BulkWriteOperation;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.WriteModel;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.mongodb.morphia.query.Query;
import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;


/**
 * Created by Peter on 3/5/2018.
 */

public class ShelterDao {
    boolean isDone = false;
   MongoFactory mongoFactory = new MongoFactory("ds133776.mlab.com",33776,"2340-test", "master2", "2340test","2340-test","");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public void saveOrUpdateDayRates(List<Shelter> dayRates) {

        // Preparing the day rate Objects to have the property month field, as well as the proper dayInvId Field

        // Prepare the bulk insert object
        com.mongodb.DBCollection collection = mongoFactory.getDatastoreDriver().getCollection(Shelter.class);
        BulkWriteOperation operation = collection.initializeUnorderedBulkOperation();

        // Split the dayRates into two operations
        // toChunkOut -> to insert and generate a new id
        // toUpdate -> id is already generated -> update this field

        List<Shelter> toChunkOut = new ArrayList<>();
        List<Shelter> toUpdate = new ArrayList<>();
        // If the iss are zero - means no id was found in database
        // add it to the toChunkOut
        // Else add it to the update operations
        for(Shelter dayRate : dayRates){
            if(dayRate.getShelterID()==0){
                toChunkOut.add(dayRate);
            }
            else {
                toUpdate.add(dayRate);
            }
        }
        // generateIdChunk increments the counter of the dayrate objects by the amount of inserts needed
        // it returns the startCounter.
        final long START_COUNTER = generateIdChunk(toChunkOut);
        long indexCounter = START_COUNTER;
        // the dayrate ids are then saved to the 'chunked out' id fields.
        for(Shelter dayRate: toChunkOut) {
            dayRate.setShelterID(indexCounter);
            indexCounter++;
        }
        // Objects are then remapped to bson format so it can be added to the bulk insert operation
        for(Shelter dayRateInsert: toChunkOut) {
            operation.insert(mongoFactory.getMorphiaDriver().getMapper().toDBObject(dayRateInsert));
        }
        // The bulk update operation is done by creating a different mongoCollection object
        MongoCollection<Document> collectionMongoUpdate = mongoFactory.getDatabaseDriver().getCollection("dayrate");
        // THe write model list is whats pushed to the server and will be updated
        List<WriteModel<Document>> updates = new ArrayList<>();

        // Add a new single operation where the _id of the dayRate is matched against the server
        // set its fields to the new object -> converted to a bson

        for(Shelter dayRateUpdate: toUpdate){
            DBObject obj =  mongoFactory.getMorphiaDriver().getMapper().toDBObject(dayRateUpdate);
            Bson bson = (Bson)obj;
            WriteModel<Document> update = new UpdateOneModel<>(
                    new Document("_id", dayRateUpdate.getShelterID()),
                    new Document("$set", bson)
            );
            updates.add(update);
        }
        // If there were no bulkUpdates don't execute - throws an exception with zero docs
        if(updates.size()!=0) {
            collectionMongoUpdate.bulkWrite(updates);
        }
        // if there where no bulk Inserts don't execute - throws an exception with zero docs
        if(toChunkOut.size()!=0){
            operation.execute();
        }
    }
    private Long generateIdChunk(List<Shelter> dayRates){
        EntityId counter;
        String collName = mongoFactory.getDatastoreDriver().getCollection(Shelter.class).getName();
        org.mongodb.morphia.query.Query<EntityId> entityIdQuery = mongoFactory.getDatastoreDriver().find(EntityId.class);
        entityIdQuery.field("_id").equals("dayrate");
        List<EntityId> counterCheck = entityIdQuery.asList();
        if (counterCheck.size()==0){
            counter = new EntityId(collName);
        }
        else{
            counter = counterCheck.get(0);
        }
        long startCount = counter.getCounter();
        counter.setCounter(dayRates.size()+startCount);
        mongoFactory.getDatastoreDriver().save(counter);
        return startCount;
    }
    public List<Shelter> getShelters() {
        Query<Shelter> shelterQuery = mongoFactory.getDatastoreDriver().createQuery(Shelter.class);
        return shelterQuery.asList();
    }
    public boolean isDone() {
        return isDone;
    }
    public class AsyncRun extends AsyncTask <Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {

        }
    }
}
