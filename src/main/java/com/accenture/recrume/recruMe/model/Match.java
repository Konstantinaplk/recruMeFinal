package com.accenture.recrume.recruMe.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

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
}