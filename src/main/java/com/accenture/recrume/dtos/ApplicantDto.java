package com.accenture.recrume.dtos;

import com.accenture.recrume.model.EducationLevel;
import com.accenture.recrume.model.ProfessionalLevel;
import com.accenture.recrume.model.Region;
import com.accenture.recrume.model.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantDto {
    private String firstName;
    private String lastName;
    private String address;
    private Region region;
    private int yob;
    private EducationLevel educationLevel;
    private ProfessionalLevel professionalLevel;
    private List<Skill> skills;
}
//
//{"firstName": "Konstantina",
//        "lastName":"Tranou",
//        "address": "Fanariou 7",
//        "region": "EPIRUS",
//        "yob": "1995",
//        "educationLevel": "BACHELOR",
//        "professionalLevel": "JUNIOR",
//        "skills": ["Java",
//        "Databases"]}