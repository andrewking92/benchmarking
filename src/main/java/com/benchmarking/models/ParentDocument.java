package com.benchmarking.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import java.util.Date;

public class ParentDocument {
    @BsonProperty("_id")
    private ObjectId id;

    @BsonProperty("attribute1")
    private String attribute1;

    @BsonProperty("attribute2")
    private Boolean attribute2;

    @BsonProperty("attribute3")
    private Integer attribute3;

    @BsonProperty("attribute4")
    private Double attribute4;

    @BsonProperty("attribute5")
    private Long attribute5;

    @BsonProperty("attribute6")
    private Float attribute6;

    @BsonProperty("attribute7")
    private Decimal128 attribute7;

    @BsonProperty("attribute8")
    private Date attribute8;

    @BsonProperty("attribute9")
    private String attribute9;

    @BsonProperty("attribute10")
    private String attribute10;

    
    public ParentDocument(String attribute1, Boolean attribute2, Integer attribute3, Double attribute4, Long attribute5, Float attribute6, Decimal128 attribute7, Date attribute8, String attribute9, String attribute10) {
        this.id = new ObjectId();
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
        this.attribute4 = attribute4;
        this.attribute5 = attribute5;
        this.attribute6 = attribute6;
        this.attribute7 = attribute7;
        this.attribute8 = attribute8;
        this.attribute9 = attribute9;
        this.attribute10 = attribute10;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public Boolean getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(Boolean attribute2) {
        this.attribute2 = attribute2;
    }

    public Integer getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(Integer attribute3) {
        this.attribute3 = attribute3;
    }

    public Double getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(Double attribute4) {
        this.attribute4 = attribute4;
    }

    public Long getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(Long attribute5) {
        this.attribute5 = attribute5;
    }
    
    public Float getAttribute6() {
        return attribute6;
    }

    public void setAttribute6(Float attribute6) {
        this.attribute6 = attribute6;
    }

    public Decimal128 getAttribute7() {
        return attribute7;
    }

    public void setAttribute7(Decimal128 attribute7) {
        this.attribute7 = attribute7;
    }

    public Date getAttribute8() {
        return attribute8;
    }

    public void setAttribute8(Date attribute8) {
        this.attribute8 = attribute8;
    }

    public String getAttribute9() {
        return attribute9;
    }

    public void setAttribute9(String attribute9) {
        this.attribute9 = attribute9;
    }

    public String getAttribute10() {
        return attribute10;
    }

    public void setAttribute10(String attribute10) {
        this.attribute10 = attribute10;
    }

}
