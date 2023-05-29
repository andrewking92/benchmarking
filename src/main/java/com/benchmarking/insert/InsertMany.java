package com.benchmarking.insert;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.BsonValue;
import com.mongodb.MongoWriteException;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.result.InsertManyResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InsertMany {
    private static final String MONGODB_URI = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "test";
    private static final String COLLECTION_NAME = "bulk";

    public static void main(String[] args) {
        ConnectionString connString = new ConnectionString(MONGODB_URI);

        MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connString)
            .build();

            try (MongoClient mongoClient = MongoClients.create(settings)) {
                // Get the database and collection
                MongoCollection<Document> collection = mongoClient.getDatabase(DATABASE_NAME)
                        .getCollection(COLLECTION_NAME);

            // Start timing
            long startTime = System.currentTimeMillis();

            // Generate 1000 documents
            List<Document> documents = new ArrayList<>();
            for (int i = 1; i <= 1000; i++) {
                Document document = new Document("key", "value" + i);
                documents.add(document);
            }

            try {
                // Insert the documents using insertMany
                InsertManyResult result = collection.insertMany(documents, new InsertManyOptions().ordered(false));

                Map<Integer, BsonValue> insertedIds = result.getInsertedIds();
                int lastIndex = insertedIds.size() - 1;
                Object count = insertedIds.get(lastIndex);

                System.out.println("Inserted documents: " + count.toString());

            } catch (MongoWriteException e) {
                System.err.println("Error during insert: " + e.getMessage());
            }

            // Stop timing
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;


            System.out.println("Execution time: " + duration + " milliseconds.");
        }
    }
}
