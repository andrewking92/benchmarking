package com.benchmarking.replace;

import com.benchmarking.models.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.WriteModel;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class BulkReplaceOne {
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

            // Start timing
            long startTime = System.currentTimeMillis();

            // Create a list of write models
            List<WriteModel<Account>> requests = new ArrayList<>();

            // Add insert operations to the list
            for (int i = 1; i <= TOTAL_DOCUMENTS; i++) {

                Bson filter = Filters.eq("name", "John Doe");
                Account account = new Account("Dohn Joe");

                requests.add(new ReplaceOneModel<>(filter, account));
            }

            // Perform bulk write
            BulkWriteResult result = collection.bulkWrite(requests, new BulkWriteOptions().ordered(false));

            // Stop timing
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            // Print the result
            System.out.println("Modified documents: " + result.getModifiedCount());

            System.out.println("Execution time: " + duration + " milliseconds.");
        }
    }
}
