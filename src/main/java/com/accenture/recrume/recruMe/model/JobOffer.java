package com.accenture.recrume.recruMe.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

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


    public JobOffer(String title, EducationLevel educationLevel, ProfessionalLevel professionalLevel, String company, Status status, Region region, Calendar dateSubmitted) {
        this.title = title;
        this.educationLevel = educationLevel;
        this.professionalLevel = professionalLevel;
        this.company = company;
        this.status = status;
        this.region = region;
        this.dateSubmitted = GregorianCalendar.getInstance().getTimeInMillis();

    }

    public Calendar getDateSubmitted() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(dateSubmitted);
        return calendar;
    }
}
