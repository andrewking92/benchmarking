package com.benchmarking.insert;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;

public class SingleInsertThreaded {
    private static final int NUM_THREADS = 10;
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

          // Create a thread pool
          ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

          // Start timing
          long startTime = System.currentTimeMillis();

          // Submit tasks to the thread pool
          for (int i = 1; i <= TOTAL_DOCUMENTS; i++) {
              int documentIndex = i;
              executor.submit(new InsertTask(collection, documentIndex));
          }

          // Stop timing
          long endTime = System.currentTimeMillis();
          long duration = endTime - startTime;

          // Shutdown the thread pool and wait for all tasks to complete
          executor.shutdown();
          while (!executor.isTerminated()) {
              // Wait for all tasks to complete
          }

          System.out.println("Concurrent insert completed. Total documents inserted: " + TOTAL_DOCUMENTS);
          System.out.println("Execution time: " + duration + " milliseconds.");
      }
  }
}