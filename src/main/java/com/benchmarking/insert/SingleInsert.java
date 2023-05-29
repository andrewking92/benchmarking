package com.benchmarking.insert;

import org.bson.Document;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;


public class SingleInsert {
    private static final String MONGODB_URI = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "test";
    private static final String COLLECTION_NAME = "bulk";

    public static void main(String[] args) {
        ConnectionString connString = new ConnectionString(MONGODB_URI);

        MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connString)
            .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            // Get the database and collection
            MongoCollection<Document> collection = mongoClient.getDatabase(DATABASE_NAME)
                    .getCollection(COLLECTION_NAME);

            // Start timing
            long startTime = System.currentTimeMillis();

            // Insert individual documents
            int count = 0;
            for (int i = 1; i <= 1000; i++) {
                Document document = new Document("key", "value" + i);
                try {
                    collection.insertOne(document);
                    count++;
                } catch (MongoWriteException e) {
                    System.err.println("Error during individual insert: " + e.getMessage());
                }
            }

            // Stop timing
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            System.out.println("Inserted documents: " + count);

            System.out.println("Execution time: " + duration + " milliseconds.");
        }
    }
}
