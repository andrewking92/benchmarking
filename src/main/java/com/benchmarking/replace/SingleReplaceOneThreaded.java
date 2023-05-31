package com.benchmarking.replace;

import com.benchmarking.models.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class SingleReplaceOneThreaded {
    private static final int NUM_THREADS = 10;
    private static final int TOTAL_DOCUMENTS = 1000;
    private static final String MONGODB_URI = System.getProperty("mongodb.uri");
    private static final String DATABASE_NAME = System.getProperty("mongodb.database");
    private static final String COLLECTION_NAME = System.getProperty("mongodb.collection");
    private static final CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
    private static final CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);


    public static void main(String[] args) {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(MONGODB_URI))
                .serverApi(serverApi)
                .codecRegistry(codecRegistry)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            // Get the database and collection
            MongoCollection<Account> collection = mongoClient.getDatabase(DATABASE_NAME)
                    .getCollection(COLLECTION_NAME, Account.class);

            // Create a thread pool
            ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

            // Start timing
            long startTime = System.currentTimeMillis();

            // Submit tasks to the thread pool
            for (int i = 1; i <= TOTAL_DOCUMENTS; i++) {
                executor.submit(new ReplaceOneTask(collection));
            }

            // Shutdown the thread pool and wait for all tasks to complete
            executor.shutdown();
            while (!executor.isTerminated()) {
                // Wait for all tasks to complete
            }

            // Stop timing
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            System.out.println("Replaced documents: " + TOTAL_DOCUMENTS);

            System.out.println("Execution time: " + duration + " milliseconds.");
        }
    }
}
