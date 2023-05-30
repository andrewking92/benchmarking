package com.benchmarking.insert;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.WriteModel;
import org.bson.Document;
import java.util.List;

public class BulkInsertTask implements Runnable {
    private final MongoCollection<Document> collection;
    private final List<WriteModel<Document>> requests;
    private final BulkWriteOptions bulkWriteOptions;

    public BulkInsertTask(MongoCollection<Document> collection, List<WriteModel<Document>> requests,
                              BulkWriteOptions bulkWriteOptions) {
        this.collection = collection;
        this.requests = requests;
        this.bulkWriteOptions = bulkWriteOptions;
    }

    @Override
    public void run() {
        BulkWriteResult result = collection.bulkWrite(requests, bulkWriteOptions);
        System.out.println("Inserted documents: " + result.getInsertedCount());
    }
}
