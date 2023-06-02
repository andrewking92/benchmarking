package com.benchmarking.models;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class Account {
    @BsonProperty("_id")
    private String id;

    @BsonProperty("name")
    private String name;

    @BsonProperty("accountKey")
    private String accountKey;

    @BsonProperty("embeddedDocument1")
    private EmbeddedDocument embeddedDocument1;

    @BsonProperty("embeddedDocument2")
    private EmbeddedDocument embeddedDocument2;

    public Account() {
        // Test record
        this.name = "John Doe";
        this.accountKey = "abcdef";
        this.embeddedDocument1 = new EmbeddedDocument();
        this.embeddedDocument2 = new EmbeddedDocument();
    }

    public Account(String name) {
        // Test record
        this.name = name;
        this.accountKey = "abcdef";
        this.embeddedDocument1 = new EmbeddedDocument();
        this.embeddedDocument2 = new EmbeddedDocument();
    }

    public Account(String name, String accountKey, EmbeddedDocument embeddedDocument1, EmbeddedDocument embeddedDocument2) {
        this.name = name;
        this.accountKey = accountKey;
        this.embeddedDocument1 = embeddedDocument1;
        this.embeddedDocument2 = embeddedDocument2;
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

    public EmbeddedDocument getEmbeddedDocument1() {
        return embeddedDocument1;
    }

    public void setEmbeddedDocument1(EmbeddedDocument embeddedDocument1) {
        this.embeddedDocument1 = embeddedDocument1;
    }

    public EmbeddedDocument getEmbeddedDocument2() {
        return embeddedDocument2;
    }

    public void setEmbeddedDocument2(EmbeddedDocument embeddedDocument2) {
        this.embeddedDocument2 = embeddedDocument2;
    }
}
