package com.accenture.recrume.dtos;

import com.accenture.recrume.model.*;
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
    private ProfessionalLevel professionalLevel;
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