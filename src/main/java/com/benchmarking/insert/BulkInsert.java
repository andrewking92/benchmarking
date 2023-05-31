package com.benchmarking.insert;

import com.benchmarking.models.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class BulkInsert {
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
                .applyToConnectionPoolSettings(builder -> builder.minSize(1))
                .codecRegistry(codecRegistry)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {

            // Get the database and collection
            MongoCollection<Account> collection = mongoClient.getDatabase(DATABASE_NAME)
                    .getCollection(COLLECTION_NAME, Account.class);

            Account account = new Account("John Doe", "abcdef", new SpecificAccountUsage("Specific Usage", "123 Main St", 10));

            // Start timing
            long startTime = System.currentTimeMillis();

            // Create a list of write models
            List<WriteModel<Account>> requests = new ArrayList<>();

            // Add insert operations to the list
            for (int i = 1; i <= TOTAL_DOCUMENTS; i++) {
                // Document document = new Document("key", "value" + i);
                requests.add(new InsertOneModel<>(account));
            }

            BulkWriteOptions bulkWriteOptions = new BulkWriteOptions().ordered(false);
 
            // Perform bulk write
            BulkWriteResult result = collection.bulkWrite(requests, bulkWriteOptions);

            // Stop timing
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            // Print the result
            System.out.println("Inserted documents: " + result.getInsertedCount());

            System.out.println("Execution time: " + duration + " milliseconds.");
        }
    }
}
