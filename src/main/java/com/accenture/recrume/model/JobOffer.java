package com.accenture.recrume.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Data
@NoArgsConstructor
@Entity
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private EducationLevel educationLevel;
    private ProfessionalLevel professionalLevel;
    private String company;
    private long dateSubmitted;
    private Status status;
    private Region region;


    public JobOffer(String company, String title,  Region region, EducationLevel educationLevel, ProfessionalLevel professionalLevel, Status status) {
        this.company = company;
        this.title = title;
        this.region = region;
        this.educationLevel = educationLevel;
        this.professionalLevel = professionalLevel;
        this.status = status;
        this.dateSubmitted = GregorianCalendar.getInstance().getTimeInMillis();

    }

    public Calendar getDateSubmitted() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(dateSubmitted);
        return calendar;
    }
}
