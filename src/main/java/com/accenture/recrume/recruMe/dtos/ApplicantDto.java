package com.accenture.recrume.recruMe.dtos;

import com.accenture.recrume.recruMe.model.EducationLevel;
import com.accenture.recrume.recruMe.model.Region;
import com.accenture.recrume.recruMe.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantDto {
    private String firstname;
    private String lastname;
    private int yob;
    private Region region;
    private Status status;
    private EducationLevel educationLevel;
}
