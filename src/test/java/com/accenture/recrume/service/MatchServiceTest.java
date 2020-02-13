package com.accenture.recrume.service;

import com.accenture.recrume.exception.MatchException;
import com.accenture.recrume.exception.MatchNotFoundException;
import com.accenture.recrume.model.*;

import com.accenture.recrume.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
@SpringBootTest
class MatchServiceTest {
    Applicant applicant;
    JobOffer jobOffer;
    Match match;
    @Autowired
    private ApplicantsRepository applicantsRepo;
    @Autowired
    private JobOffersRepository jobOffersRepo;
    @Autowired
    private MatchService matchService;
    @Autowired
    private MatchesRepository matchesRepo;
    @Autowired
    private JobSkillsRepository jobSkillsRepo;
    @Autowired
    private AppSkillsRepository appSkillsRepo;



    @BeforeEach
    void setUp() {
        matchService = new MatchService(matchesRepo,applicantsRepo,jobOffersRepo,jobSkillsRepo, appSkillsRepo);
        applicant = applicantsRepo.getApplicantById(3);
        applicant.setStatus(Status.INACTIVE);
        jobOffer = jobOffersRepo.findById(1);
        match = new Match();
        match.setMatchStatus(MatchStatus.UNFINALIZED);
        match.setMatchType(MatchType.MANUAL);
        match.setApplicant(applicant);
        match.setJobOffer(jobOffer);
    }

    @Test
    void addManualMatch() throws MatchException {
        assertEquals(match.getApplicant(), matchService.addManualMatch(3, 1).getApplicant());
    }

    @Test
    void deleteMatch() throws MatchNotFoundException {
        match.setMatchStatus(MatchStatus.DELETED);
        assertEquals(match.getMatchStatus(), matchService.deleteMatch(matchesRepo.getMatchByAppId(applicant.getId()).getId()).getMatchStatus() );
    }

    @Test
    void finalizeMatch() throws MatchNotFoundException {
        match.setMatchStatus(MatchStatus.FINALIZED);
        assertEquals(match.getMatchStatus(), matchService.finalizeMatch(matchesRepo.getMatchByAppId(applicant.getId()).getId()).getMatchStatus() );
    }
}