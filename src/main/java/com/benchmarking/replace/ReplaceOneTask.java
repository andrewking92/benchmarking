package com.benchmarking.replace;

import com.benchmarking.models.*;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;

public class ReplaceOneTask implements Runnable {
    private final MongoCollection<Account> collection;

    public ReplaceOneTask(MongoCollection<Account> collection) {
        this.collection = collection;
    }

    @Override
    public void run() {

        Bson filter = Filters.eq("name", "John Doe");
        Account account = new Account(1, "Dohn Joe");

        try {
            collection.replaceOne(filter, account);
        } catch (MongoWriteException e) {
            System.err.println("Error during individual insert: " + e.getMessage());
        }
    }
}