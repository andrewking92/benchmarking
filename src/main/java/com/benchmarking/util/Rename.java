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

public class Rename {
    private static final String MONGODB_URI = System.getProperty("mongodb.uri");
    private static final String ADMIN_DATABASE_NAME = "admin";
    private static final String DATABASE_NAME = System.getProperty("mongodb.database");
    private static final String COLLECTION_NAME = System.getProperty("mongodb.collection");
    private static final String COLLECTION_NAME_TO = System.getProperty("mongodb.collection.to");

    public static void main(String[] args) {

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
                MongoDatabase database = mongoClient.getDatabase(ADMIN_DATABASE_NAME);

                // Start timing
                long startTime = System.currentTimeMillis();

                // Build the ping command
                Document command = new Document("renameCollection", DATABASE_NAME + "." + COLLECTION_NAME)
                        .append("to", DATABASE_NAME + "." + COLLECTION_NAME_TO);

                // Execute the ping command
                database.runCommand(command);

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
