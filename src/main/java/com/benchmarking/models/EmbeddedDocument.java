package com.benchmarking.models;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class EmbeddedDocument {
    @BsonProperty("name")
    private String name;

    @BsonProperty("address")
    private String address;

    @BsonProperty("size")
    private int size;

    public EmbeddedDocument() {
        // Test record
        this.name = "Specific Usage";
        this.address = "123 Main St";
        this.size = 50;
    }

    public EmbeddedDocument(String name, String address, int size) {
        this.name = name;
        this.address = address;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
