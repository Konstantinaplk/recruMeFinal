package com.accenture.recrume.recruMe.model;

public enum MatchType {
    AUTOMATIC(0),
    MANUAL(1);

    private int dbValue;

    MatchType(int dbValue){
        this.dbValue = dbValue;
    }

    public int getValueToDb(){
        return dbValue;
    }
}
