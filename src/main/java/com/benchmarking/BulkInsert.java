package com.benchmarking;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;
import org.bson.Document;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.client.model.BulkWriteOptions;

import java.util.ArrayList;
import java.util.List;

public class BulkInsert {
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

            // Create a list of write models
            List<WriteModel<Document>> requests = new ArrayList<>();

            // Add insert operations to the list
            for (int i = 1; i <= 1000; i++) {
                Document document = new Document("key", "value" + i);
                requests.add(new InsertOneModel<>(document));
            }

            // Perform bulk write
            BulkWriteResult result = collection.bulkWrite(requests, new BulkWriteOptions().ordered(false));

            // Stop timing
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            // Print the result
            System.out.println("Inserted documents: " + result.getInsertedCount());

            System.out.println("Execution time: " + duration + " milliseconds.");
        }
    }
}
