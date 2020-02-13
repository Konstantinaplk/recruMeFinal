package com.accenture.recrume.recruMe.service;

import com.accenture.recrume.recruMe.exception.MatchException;
import com.accenture.recrume.recruMe.model.*;
import com.accenture.recrume.recruMe.repository.*;
import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

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

    @AfterEach
    void tearDown() {
    }

    @Test
    void addManualMatch() throws MatchException {
        assertEquals(match.getApplicant(), matchService.addManualMatch(3, 1).getApplicant());
    }

//    @Test
//    void deleteMatch() {
//        assertEquals(match.getMatchStatus(), matchService.deleteMatch(matchesRepo.getMatchByAppId(applicant.getId()).getMatchStatus()) );
//    }

    @Test
    void finalizeMatch() {
    }

    @Test
    void createAutomaticMatches() {
    }

    @Test
    void viewProposedUnfinalizdMatches() {
    }

    @Test
    void getOrderedMatchesByDate() {
    }
}