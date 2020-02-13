package com.accenture.recrume.model;

public enum Status {

    INACTIVE(0),
    ACTIVE(1);

    private int dbValue;

    Status (int dbValue){
        this.dbValue = dbValue;
    }

    public int getValueToDb(){
        return dbValue;
    }
}
