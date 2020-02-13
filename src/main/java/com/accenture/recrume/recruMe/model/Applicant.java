package com.accenture.recrume.recruMe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private int yob;
    private Region region;
    @Column(columnDefinition = "bit default 1")
    private Status status;
    private String address;
    private EducationLevel educationLevel;
    private ProfessionalLevel professionalLevel;

    public Applicant(String firstName, String lastName, String address,  Region region,
            EducationLevel educationLevel, ProfessionalLevel professionalLevel, int yob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.region = region;
        this.educationLevel = educationLevel;
        this.professionalLevel = professionalLevel;
        this.yob = yob;
    }
}
