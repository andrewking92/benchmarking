package com.benchmarking.insert;

import com.benchmarking.models.*;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.WriteModel;
import java.util.List;

public class BulkInsertTask implements Runnable {
    private final MongoCollection<ParentDocument> collection;
    private final List<WriteModel<ParentDocument>> requests;
    private final BulkWriteOptions bulkWriteOptions;

    public BulkInsertTask(MongoCollection<ParentDocument> collection, List<WriteModel<ParentDocument>> requests,
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
