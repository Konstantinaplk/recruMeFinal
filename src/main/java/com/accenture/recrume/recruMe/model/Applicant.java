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
    private EducationLevel educationLevel;

    public Applicant(String firstName, String lastName, int yob, Region region, EducationLevel educationLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yob = yob;
        this.region = region;
        this.status = Status.ACTIVE;
        this.educationLevel = educationLevel;
    }
}
