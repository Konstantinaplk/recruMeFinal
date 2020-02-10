package com.accenture.recrume.recruMe.service;

import com.accenture.recrume.recruMe.exception.MatchException;
import com.accenture.recrume.recruMe.exception.MatchNotFoundException;
import com.accenture.recrume.recruMe.model.*;
import com.accenture.recrume.recruMe.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private MatchesRepository matchesRepo;
    private ApplicantsRepository applicantsRepo;
    private JobOffersRepository jobOffersRepo;
    private JobOfferService jobOfferService;
    private ApplicantService applicantService;

    @Autowired
    public MatchService(MatchesRepository matchesRepo, ApplicantsRepository applicantsRepo, JobOffersRepository jobOffersRepo) {
        this.matchesRepo = matchesRepo;
        this.applicantsRepo = applicantsRepo;
        this.jobOffersRepo = jobOffersRepo;
    }

     /**
     * Manual match of an applicant and a jobOffer only if there are available. Update the Match Table
     * with a new unfinalized manual match.
     * @param appId Integer which refers to the applicant's id
     * @param jobOfferId Integer which refers to the jobOffer's id
     * @throws MatchException when the applicant or the jobOffer is not active - available
     */
    public void addManualMatch(int appId, int jobOfferId) throws MatchException {
        Match match = new Match();
        Applicant applicant = applicantsRepo.getApplicantById(appId);
        if (applicant.getStatus()!=Status.ACTIVE){throw new MatchException("Applicant is already matched"); }
        JobOffer jobOffer = jobOffersRepo.findById(jobOfferId);
        if (jobOffer.getStatus()!=Status.ACTIVE){throw new MatchException("JobOffer is already matched");}
        match.setApplicant(applicant);
        jobOffer.setStatus(Status.INACTIVE);
        match.setJobOffer(jobOffer);
        jobOffer.setStatus(Status.INACTIVE);
        match.setMatchType(MatchType.MANUAL);
        match.setMatchStatus(MatchStatus.UNFINALIZED);
        matchesRepo.save(match);
    }

    /**
     * takes an match id and execute a soft delete (field MatchStatus -> deleted) with the
     * applicant and the jobOffer becoming Active again.
     * @param matchId Integer which represents the id of a certain match
     * @throws MatchNotFoundException when the match id doesn't exist.
     */
    public void deleteMatch(int matchId) throws MatchNotFoundException {
        Match match = matchesRepo.getMatchById(matchId);
        if(match == null){throw new MatchNotFoundException("There is no match with this Id");}
        match.setMatchStatus(MatchStatus.DELETED);
        Applicant applicant = applicantsRepo.getApplicantById(match.getApplicant().getId());
        applicant.setStatus(Status.ACTIVE);
        JobOffer jobOffer = jobOffersRepo.findById(match.getJobOffer().getId());
        jobOffer.setStatus(Status.ACTIVE);
    }

    public void finalizeMatch(int matchId) throws MatchNotFoundException {
        Match match = matchesRepo.getMatchById(matchId);
        if(match == null){throw new MatchNotFoundException("There is no match with this Id");}
        match.setMatchStatus(MatchStatus.FINALIZED);
    }
}
