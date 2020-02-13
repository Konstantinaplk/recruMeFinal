package com.accenture.recrume.recruMe.service;

import com.accenture.recrume.recruMe.exception.JobOfferException;
import com.accenture.recrume.recruMe.exception.MatchException;
import com.accenture.recrume.recruMe.exception.MatchNotFoundException;
import com.accenture.recrume.recruMe.model.*;
import com.accenture.recrume.recruMe.repository.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@NoArgsConstructor
public class MatchService {

    private MatchesRepository matchesRepo;
    private ApplicantsRepository applicantsRepo;
    private AppSkillsRepository appSkillsRepo;
    private JobOffersRepository jobOffersRepo;
    private JobOfferService jobOfferService;
    private ApplicantService applicantService;
    private JobSkillsRepository jobSkillsRepo;

    @Autowired
    public MatchService(MatchesRepository matchesRepo, ApplicantsRepository applicantsRepo
            , JobOffersRepository jobOffersRepo, JobSkillsRepository jobSkillsRepo
            , AppSkillsRepository appSkillsRepo) {
        this.matchesRepo = matchesRepo;
        this.applicantsRepo = applicantsRepo;
        this.jobOffersRepo = jobOffersRepo;
        this.jobSkillsRepo = jobSkillsRepo;
        this.appSkillsRepo = appSkillsRepo;

    }

    /**
     * Manual match of an applicant and a jobOffer only if there are available. Update the Match Table
     * with a new unfinalized manual match.
     *
     * @param appId      Integer which refers to the applicant's id.
     * @param jobOfferId Integer which refers to the jobOffer's id.
     * @throws MatchException when the applicant or the jobOffer is not active - available.
     */
    public Match addManualMatch(int appId, int jobOfferId) throws MatchException {
        Match match = new Match();
        Applicant applicant = applicantsRepo.getApplicantById(appId);
        if (applicant.getStatus() != Status.ACTIVE) {
            throw new MatchException("Applicant is already matched");
        }
        JobOffer jobOffer = jobOffersRepo.findById(jobOfferId);
        if (jobOffer.getStatus() != Status.ACTIVE) {
            throw new MatchException("JobOffer is already matched");
        }
        match.setApplicant(applicant);
        applicant.setStatus(Status.INACTIVE);
        match.setJobOffer(jobOffer);
        jobOffer.setStatus(Status.INACTIVE);
        match.setMatchType(MatchType.MANUAL);
        match.setMatchStatus(MatchStatus.UNFINALIZED);
        match.setDateSubmitted(GregorianCalendar.getInstance().getTimeInMillis());
        matchesRepo.save(match);
        return match;
    }

    /**
     * Takes an match id from the fronend and execute a soft delete
     * (field MatchStatus -> deleted) with the
     * applicant and the jobOffer becoming Active again.
     *
     * @param matchId Integer which represents the id of a certain match.
     * @throws MatchNotFoundException when the match id doesn't exist.
     */
    public Match deleteMatch(int matchId) throws MatchNotFoundException {
        Match match = matchesRepo.getMatchById(matchId);
        if (match == null) {
            throw new MatchNotFoundException("There is no match with this Id");
        }
        match.setMatchStatus(MatchStatus.DELETED);
        matchesRepo.save(match);
        Applicant applicant = applicantsRepo.getApplicantById(match.getApplicant().getId());
        applicant.setStatus(Status.ACTIVE);
        applicantsRepo.save(applicant);
        JobOffer jobOffer = jobOffersRepo.findById(match.getJobOffer().getId());
        jobOffer.setStatus(Status.ACTIVE);
        jobOffersRepo.save(jobOffer);
        return match;
    }


    /**
     * Takes a certain MatchId from the fronend and make its Status finalized.
     *
     * @param matchId Integer which refers to the Id of a certain match.
     * @throws MatchNotFoundException when match doesn't exist.
     */
    public Match finalizeMatch(int matchId) throws MatchNotFoundException {
        Match match = matchesRepo.getMatchById(matchId);
        if (match == null) {
            throw new MatchNotFoundException("There is no match with this Id");
        }
        match.setMatchStatus(MatchStatus.FINALIZED);
        match.setDateSubmitted(GregorianCalendar.getInstance().getTimeInMillis());
        matchesRepo.save(match);
        return match;
    }

    /**
     * Check if each job offer can have an automatic match, which means that all job skills fits to an applicants skills.
     * If it does, it calls the checkForAutomaticMatch otherwise it catches an exception.
     */
    public void createAutomaticMatches() {
        List<Integer> listActiveJobOffersId = jobOffersRepo.getActiveJobOfferId();
        for (int jobOfferId : listActiveJobOffersId) {
//                checkForAutomaticMatch(jobOfferId);
            try {
                checkForAutomaticMatch(jobOfferId);
            } catch (MatchException | JobOfferException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Check for a specific match if all of its skills fits to an applicant skills. If it does, it calls the createNewAutoMatch.
     * @param jobOfferId A specific job offer Id.
     * @throws MatchException if there is no match for this JobOffer
     * @throws JobOfferException if there is no skill required for the current jobOffer
     */
    private void checkForAutomaticMatch(int jobOfferId) throws MatchException, JobOfferException {
        List<Integer> listJobSkillsId = jobSkillsRepo.getSkillsByJobId(jobOfferId);
        System.out.println(listJobSkillsId);
        List<Integer> listActiveAppId = applicantsRepo.getActiveApplicants();
        System.out.println(listActiveAppId);
        if (listJobSkillsId.isEmpty()) {
            throw new JobOfferException("There is no skill required for the current jobOffer");
        } else {
            boolean matched = false;
            for (Integer appId : listActiveAppId) {
                if (applicantsRepo.getApplicantById(appId).getStatus().equals(Status.ACTIVE)) {
                    Boolean check = checkApplicantFits(listJobSkillsId, appId);
                    if (check) {
                        createNewAutoMatch(appId, jobOfferId);
                        jobOffersRepo.findById(jobOfferId).setStatus(Status.INACTIVE);
                        matched = true;
                        break;
                    }
                }
            }
            if (!matched) {
                throw new MatchException("there is no match for JobOffer: " + jobOfferId);
            }
        }
    }

    /**
     * Create a New Auto Match and save it to database. It also makes this job offer and this applicant to inactive.
     * @param appId A specific applicant id
     * @param jobId A specific job offer id
     */
    private void createNewAutoMatch(int appId, int jobId) {
        Match match = new Match();
        match.setApplicant(applicantsRepo.getApplicantById(appId));
        match.setJobOffer(jobOffersRepo.findById(jobId));
        match.setMatchStatus(MatchStatus.UNFINALIZED);
        match.setMatchType(MatchType.AUTOMATIC);
        match.setDateSubmitted(GregorianCalendar.getInstance().getTimeInMillis());
        matchesRepo.save(match);
        Applicant applicant =  applicantsRepo.getApplicantById(appId);
        applicant.setStatus(Status.INACTIVE);
        applicantsRepo.save(applicant);
        JobOffer jobOffer = jobOffersRepo.findById(jobId);
        jobOffer.setStatus(Status.INACTIVE);
        jobOffersRepo.save(jobOffer);
    }


    /**
     * Check if the applicant has all the skills provided
     * @param listJobSkillsId A listo of skill ids.
     * @param appId A specific applicant id
     * @return A boolean variable. If true all skills matches to this customer.
     */
    private Boolean checkApplicantFits(List<Integer> listJobSkillsId, int appId) {
        boolean check = true;
        for (Integer skillId : listJobSkillsId) {
            if (appSkillsRepo.ApplicantHasSkill(appId, skillId) != null) {
            } else {
                check = false;
                break;
            }
        }
        if (check) {
            applicantsRepo.getApplicantById(appId).setStatus(Status.INACTIVE);
        }
        return check;
    }

    /**
     * Retrieve the list of the proposed matches applicants-offers
     *
     * @return List of Matches
     */
    public List<Match> viewProposedUnfinalizdMatches() {
        List<Match> matches = matchesRepo.getAutomaticUnfinalizedMatches();
        return matches;
    }

    public List<Match> getOrderedMatchesByDate(){
        return matchesRepo.getOrderedMatchesByDate().subList(0,2);
    }
}
