package com.accenture.recrume.recruMe.model;

public enum SkillLevels {
    FAIR(1),
    GOOD(2),
    VERY_GOOD(3),
    EXCELLENT(4);

    private int dbValue;

    SkillLevels(int dbValue){
        this.dbValue = dbValue;
    }

    public int getValueToDb(){
        return dbValue;
    }
}

