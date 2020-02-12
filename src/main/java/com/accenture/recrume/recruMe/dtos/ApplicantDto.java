package com.accenture.recrume.recruMe.dtos;

import com.accenture.recrume.recruMe.model.EducationLevel;
import com.accenture.recrume.recruMe.model.Region;
import com.accenture.recrume.recruMe.model.Skill;
import com.accenture.recrume.recruMe.model.Status;
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
    private int yob;
    private Region region;
    private Status status;
    private EducationLevel educationLevel;
    private String address;
    private List<Skill> skills;
}
