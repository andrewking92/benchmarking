package com.benchmarking.insert;

import org.bson.Document;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;

public class InsertTask implements Runnable {
    private final MongoCollection<Document> collection;
    private final int documentIndex;

    public InsertTask(MongoCollection<Document> collection, int documentIndex) {
        this.collection = collection;
        this.documentIndex = documentIndex;
    }

    @Override
    public void run() {
        Document document = new Document("key", "value" + documentIndex);
        try {
            collection.insertOne(document);
        } catch (MongoWriteException e) {
            System.err.println("Error during insert: " + e.getMessage());
        }
    }
}