package com.benchmarking.models;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class Account {
    @BsonProperty("_id")
    private String id;

    @BsonProperty("name")
    private String name;

    @BsonProperty("accountKey")
    private String accountKey;

    @BsonProperty("specificAccountUsage")
    private SpecificAccountUsage specificAccountUsage;

    public Account() {
        // Test record
        this.name = "John Doe";
        this.accountKey = "abcdef";
        this.specificAccountUsage = new SpecificAccountUsage("Specific Usage", "123 Main St", 10);
    }

    public Account(String name, String accountKey, SpecificAccountUsage specificAccountUsage) {
        this.name = name;
        this.accountKey = accountKey;
        this.specificAccountUsage = specificAccountUsage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    public SpecificAccountUsage getSpecificAccountUsage() {
        return specificAccountUsage;
    }

    public void setSpecificAccountUsage(SpecificAccountUsage specificAccountUsage) {
        this.specificAccountUsage = specificAccountUsage;
    }
}
