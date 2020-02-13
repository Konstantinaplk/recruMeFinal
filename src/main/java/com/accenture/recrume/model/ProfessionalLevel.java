package com.accenture.recrume.model;

public enum ProfessionalLevel {
    ENTRY(1),
    JUNIOR(2),
    MID(3),
    SENIOR(4);

    private int dbValue;

    ProfessionalLevel(int dbValue){
        this.dbValue = dbValue;
    }

    public int getValueToDb(){
        return dbValue;
    }
}

