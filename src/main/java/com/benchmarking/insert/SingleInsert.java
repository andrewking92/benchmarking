package com.benchmarking.insert;

import com.benchmarking.models.*;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class SingleInsert {
    private static final int TOTAL_DOCUMENTS = 1000;
    private static final String MONGODB_URI = System.getProperty("mongodb.uri");
    private static final String DATABASE_NAME = "test";
    private static final String COLLECTION_NAME = "bulk";
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

            Account account = new Account();
            account.setName("John Doe");
            account.setAccountKey("abcdef");

            SpecificAccountUsage specificAccountUsage = new SpecificAccountUsage();
            specificAccountUsage.setName("Specific Usage");
            specificAccountUsage.setAddress("123 Main St");
            specificAccountUsage.setSize(10);
            account.setSpecificAccountUsage(specificAccountUsage);

            // Start timing
            long startTime = System.currentTimeMillis();

            // Insert individual documents
            int count = 0;
            for (int i = 1; i <= TOTAL_DOCUMENTS; i++) {
                try {
                    collection.insertOne(account);
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
