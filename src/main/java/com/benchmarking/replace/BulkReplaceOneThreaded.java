package com.benchmarking.replace;

import com.benchmarking.models.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.WriteModel;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.model.Filters;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;

public class BulkReplaceOneThreaded {
    private static final int NUM_THREADS = Integer.parseInt(System.getProperty("mongodb.threads"));
    private static final int TOTAL_DOCUMENTS = Integer.parseInt(System.getProperty("mongodb.documents"));
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

                    List<WriteModel<Account>> requests = new ArrayList<>();

                    // List<ObjectId> objectIds = getObjectIds(collection);

                    // Account account = new Account("New Name");

                    // for (ObjectId id : objectIds) {
                    //     Bson filter = Filters.eq("_id", id);

                    //     requests.add(new ReplaceOneModel<>(filter, account, new ReplaceOptions().upsert(true)));
                    // }

                    for (int i = 1; i <= TOTAL_DOCUMENTS; i++) {
                        Account account = new Account("Replaced Name");
                        Bson filter = Filters.eq("_id", account.getId());
                        // new ReplaceOptions().upsert(true)
                        requests.add(new ReplaceOneModel<>(filter, account));
                    }

                    BulkWriteOptions bulkWriteOptions = new BulkWriteOptions().ordered(false);

                    // Create a thread pool with the specified number of threads
                    ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

                    // Start timing
                    // long startTime = System.currentTimeMillis();

                    // Calculate the chunk size per thread
                    int chunkSize = requests.size() / NUM_THREADS;

                    for (int i = 0; i < NUM_THREADS; i++) {
                        int startIndex = i * chunkSize;
                        int endIndex = (i + 1) * chunkSize;

                        if (i == NUM_THREADS - 1) {
                            endIndex = requests.size();
                        }

                        List<WriteModel<Account>> chunk = requests.subList(startIndex, endIndex);

                        // Submit the task to the executor service
                        executorService.submit(new BulkReplaceOneTask(collection, chunk, bulkWriteOptions));
                    }

                    // Shutdown the executor service and wait for all tasks to complete
                    executorService.shutdown();

                    try {
                        // Wait for all tasks to complete or timeout after a specific duration
                        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                            // Handle the case when tasks are not completed within the specified timeout
                            System.out.println("Timeout occurred while waiting for task completion.");
                        }
                    } catch (InterruptedException e) {
                        // Handle the InterruptedException
                        System.out.println("Interrupted while waiting for task completion: " + e.getMessage());
                        Thread.currentThread().interrupt();
                    }

                    // long endTime = System.currentTimeMillis();
                    // long duration = endTime - startTime;

            // Print the result
            // System.out.println("Execution time: " + duration + " milliseconds.");
        }
    }

    // public static List<ObjectId> getObjectIds(MongoCollection<Account> collection) {
    //     List<ObjectId> ids = new ArrayList<>();
    //     try (MongoCursor<Account> cursor = collection.find().iterator()) {
    //         while (cursor.hasNext()) {
    //             Account doc = cursor.next();
    //             ids.add(doc.getId());
    //         }
    //     }
    //     return ids;
    // }
}
