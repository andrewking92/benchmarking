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

public class Ping {
    private static final int TOTAL_ITER = Integer.parseInt(System.getProperty("mongodb.count"));
    private static final String MONGODB_URI = System.getProperty("mongodb.uri");
    private static final String DATABASE_NAME = System.getProperty("mongodb.database");

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
                MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

                long totalDuration = 0;

                for (int i = 0; i < TOTAL_ITER; i++) {
                    long startTime = System.currentTimeMillis();
    
                    // Build the ping command
                    Document command = new Document("ping", 1);
    
                    // Execute the ping command
                    database.runCommand(command);
    
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;
                    totalDuration += duration;
                }
    
                double averageResponseTime = (double) totalDuration / TOTAL_ITER;

                System.out.println("Pinged deployment " + TOTAL_ITER + " times.");
                System.out.println("Execution time: " + totalDuration + " milliseconds.");
                System.out.println("Average response time per ping command: " + averageResponseTime + " milliseconds");

            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}
