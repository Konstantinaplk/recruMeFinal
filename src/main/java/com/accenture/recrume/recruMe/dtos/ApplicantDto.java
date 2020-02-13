package com.accenture.recrume.recruMe.dtos;

import com.accenture.recrume.recruMe.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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
