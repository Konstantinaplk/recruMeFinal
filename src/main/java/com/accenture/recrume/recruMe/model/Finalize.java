package com.accenture.recrume.recruMe.model;

public enum Finalize {
    TRUE("T"),
    FALSE("F");

    private String dbValue;

    Finalize(String dbValue){
        this.dbValue = dbValue;
    }

    public String getValueToDb(){
        return dbValue;
    }
}
