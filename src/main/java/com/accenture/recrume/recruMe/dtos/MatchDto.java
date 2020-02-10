package com.accenture.recrume.recruMe.dtos;

import com.accenture.recrume.recruMe.model.Applicant;
import com.accenture.recrume.recruMe.model.JobOffer;
import com.accenture.recrume.recruMe.model.MatchStatus;
import com.accenture.recrume.recruMe.model.MatchType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchDto {
    private Applicant appId;
    private JobOffer jobId;
    private MatchStatus matchStatus;
    private MatchType matchType;
}

