package com.accenture.recrume.dtos;

import com.accenture.recrume.model.Applicant;
import com.accenture.recrume.model.MatchStatus;
import com.accenture.recrume.model.MatchType;
import com.accenture.recrume.model.JobOffer;
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

