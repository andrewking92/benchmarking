package com.benchmarking.replace;

import com.benchmarking.models.*;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;

public class ReplaceOneTask implements Runnable {
    private final MongoCollection<Account> collection;

    public ReplaceOneTask(MongoCollection<Account> collection) {
        this.collection = collection;
    }

    @Override
    public void run() {

        Bson filter = Filters.eq("name", "John Doe");
        Account account = new Account("Dohn Joe", "abcdef", new SpecificAccountUsage("Specific Usage", "123 Main St", 10));

        try {
            UpdateResult result = collection.replaceOne(filter, account);
        } catch (MongoWriteException e) {
            System.err.println("Error during individual insert: " + e.getMessage());
        }
    }
}