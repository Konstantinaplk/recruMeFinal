package com.accenture.recrume.recruMe.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Data
@NoArgsConstructor
@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Applicant applicant;
    @ManyToOne
    private  JobOffer jobOffer;

    private MatchStatus matchStatus;
    private MatchType matchType;
    private long dateSubmitted;

    public Calendar getDateSubmitted() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(dateSubmitted);
        return calendar;
    }
}