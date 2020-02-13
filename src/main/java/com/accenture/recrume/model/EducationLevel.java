package com.accenture.recrume.model;

public enum EducationLevel {

    SECONDARY(0),
    BACHELOR(1),
    MASTER(2),
    PHD(3);

    private int dbValue;

    EducationLevel(int dbValue){
        this.dbValue = dbValue;
    }

    public int getValueToDb(){
        return dbValue;
    }
}
