package com.benchmarking.util;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Drop {
    private static final String MONGODB_URI = System.getProperty("mongodb.uri");
    private static final String DATABASE_NAME = System.getProperty("mongodb.database");

    public static void main(String[] args) {
        String COLLECTION_NAME = args[2];

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(MONGODB_URI))
                .serverApi(serverApi)
                .build();

        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

                // Start timing
                long startTime = System.currentTimeMillis();

                database.runCommand(new Document("drop", COLLECTION_NAME));

                // Stop timing
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;

                System.out.println("Dropped collection: " + COLLECTION_NAME);
                System.out.println("Execution time: " + duration + " milliseconds.");

            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}
