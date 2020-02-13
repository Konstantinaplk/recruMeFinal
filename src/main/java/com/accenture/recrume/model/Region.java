package com.accenture.recrume.model;

public enum Region {

    ATTICA(0),
    CENTRAL_GREECE (1),
    MACEDONIA(2),
    CRETE(3),
    THRACE(4),
    THESSALY(5),
    PELOPONNESE(6),
    AEGEAN(7),
    EPIRUS(8);

    private int dbValue;

    Region(int dbValue){
        this.dbValue = dbValue;
    }

    public int getValueToDb(){
        return dbValue;
    }

    public Region getValue(String region){
        return Region.valueOf(region);
    }


}
