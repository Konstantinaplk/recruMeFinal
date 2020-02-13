package com.accenture.recrume.model;

public enum MatchStatus {
    UNFINALIZED("0"),
    FINALIZED("1"),
    DELETED("2");

    private String dbValue;

    MatchStatus(String dbValue){
        this.dbValue = dbValue;
    }

    public String getValueToDb(){
        return dbValue;
    }
}
