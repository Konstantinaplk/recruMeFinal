package com.accenture.recrume.recruMe.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class JobSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn
    private JobOffer jobOfferId;
    @ManyToOne
    @JoinColumn
    private Skill skillId;
}
