package com.benchmarking.insert;

import org.bson.Document;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;

public class SingleInsert {
    private static final int TOTAL_DOCUMENTS = 1000;
    private static final String MONGODB_URI = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "test";
    private static final String COLLECTION_NAME = "bulk";

    public static void main(String[] args) {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(MONGODB_URI))
                .serverApi(serverApi)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            // Get the database and collection
            MongoCollection<Document> collection = mongoClient.getDatabase(DATABASE_NAME)
                    .getCollection(COLLECTION_NAME);

            // Start timing
            long startTime = System.currentTimeMillis();

            // Insert individual documents
            int count = 0;
            for (int i = 1; i <= TOTAL_DOCUMENTS; i++) {
                Document document = new Document("key", "value" + i);
                try {
                    InsertOneResult result = collection.insertOne(document);
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