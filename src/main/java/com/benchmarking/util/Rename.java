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
    private static final String DATABASE_NAME = System.getProperty("mongodb.database");

    public static void main(String[] args) {
        String ADMIN_DATABASE_NAME = "admin";
        String SRC_COLLECTION = args[2];
        String DST_COLLECTION = args[3];

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
                Document command = new Document("renameCollection", DATABASE_NAME + "." + SRC_COLLECTION)
                        .append("to", DATABASE_NAME + "." + DST_COLLECTION);

                // Execute the ping command
                database.runCommand(command);

                // Stop timing
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;

                System.out.println("Renamed collection: " + SRC_COLLECTION + " to " + DST_COLLECTION);
                System.out.println("Execution time: " + duration + " milliseconds.");

            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}
