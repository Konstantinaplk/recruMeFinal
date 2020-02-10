package com.accenture.recrume.recruMe.dtos;

import com.accenture.recrume.recruMe.model.EducationLevel;
import com.accenture.recrume.recruMe.model.Region;
import com.accenture.recrume.recruMe.model.Skill;
import com.accenture.recrume.recruMe.model.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JobOfferDto {
    private String company;
    private Region region;
    private String title;
    private EducationLevel educationLevel;
    private long dateSubmitted;
    private Status status;
    private List<Skill> skills;
}


//{"companyName": "Accenture",
//        "region": Region.ATTICA}

//{"company": "Accenture",
//        "region": "ATTICA",
//        "title": "javadeve",
//        "educationLevel": "MASTER",
//        "dateSubmitted": "",
//        "status": "ACTIVE"}