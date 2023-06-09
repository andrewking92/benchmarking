package com.benchmarking.insert;

import com.benchmarking.models.*;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;

public class InsertTask implements Runnable {
    private final MongoCollection<Account> collection;

    public InsertTask(MongoCollection<Account> collection) {
        this.collection = collection;
    }

    @Override
    public void run() {
        Account account = new Account();

        try {
            collection.insertOne(account);
        } catch (MongoWriteException e) {
            System.err.println("Error during insert: " + e.getMessage());
        }
    }
}