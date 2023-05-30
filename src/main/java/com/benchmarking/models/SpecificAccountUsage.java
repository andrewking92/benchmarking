package com.benchmarking.models;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class SpecificAccountUsage {
    @BsonProperty("name")
    private String name;

    @BsonProperty("address")
    private String address;

    @BsonProperty("size")
    private int size;

    public SpecificAccountUsage() {}

    public SpecificAccountUsage(String name, String address, int size) {
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
