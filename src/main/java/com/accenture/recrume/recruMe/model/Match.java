package com.accenture.recrume.recruMe.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Applicant appId;
    @ManyToOne
    private  JobOffer jobId;

    private Finalize finalize;
    private MatchType matchType;
}