package com.benchmarking.insert;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;
import org.bson.Document;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;

import java.util.ArrayList;
import java.util.List;

public class BulkInsert {
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
                .applyToConnectionPoolSettings(builder -> builder.minSize(1))
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {

            // Get the database and collection
            MongoCollection<Document> collection = mongoClient.getDatabase(DATABASE_NAME)
                    .getCollection(COLLECTION_NAME);

            // Start timing
            long startTime = System.currentTimeMillis();

            // Create a list of write models
            List<WriteModel<Document>> requests = new ArrayList<>();

            // Add insert operations to the list
            for (int i = 1; i <= TOTAL_DOCUMENTS; i++) {
                Document document = new Document("key", "value" + i);
                requests.add(new InsertOneModel<>(document));
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