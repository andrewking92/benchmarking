package com.benchmarking.replace;

import com.benchmarking.models.*;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.WriteModel;
import java.util.List;

public class BulkReplaceOneTask implements Runnable {
    private final MongoCollection<Account> collection;
    private final List<WriteModel<Account>> requests;
    private final BulkWriteOptions bulkWriteOptions;

    public BulkReplaceOneTask(MongoCollection<Account> collection, List<WriteModel<Account>> requests,
                              BulkWriteOptions bulkWriteOptions) {
        this.collection = collection;
        this.requests = requests;
        this.bulkWriteOptions = bulkWriteOptions;
    }

    @Override
    public void run() {
        BulkWriteResult result = collection.bulkWrite(requests, bulkWriteOptions);
        System.out.println("Inserted documents: " + result.getModifiedCount());
    }
}
