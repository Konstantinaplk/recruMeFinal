package com.accenture.recrume.controller;

import com.accenture.recrume.exception.JobOfferException;
import com.accenture.recrume.exception.MatchException;
import com.accenture.recrume.exception.MatchNotFoundException;
import com.accenture.recrume.model.Match;
import com.accenture.recrume.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("match")
public class MatchController {

    private MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    /**
     * POST endpoint which adds a manual, unfinalized match of an applicant and a jobOffer in Match table.
     * @param appId Integer which refers to an applicant's id.
     * @param jobOfferId Integer which refers to an jobOffer's id.
     */
    @PostMapping("appId/{appId}/jobOffer/{jobOfferId}")
    public Match addManualMatch(@PathVariable int appId, @PathVariable int jobOfferId) throws MatchException {
        return matchService.addManualMatch(appId, jobOfferId);
    }

    /**
     * PUT endpoint which deletes a certain matching softly, defining matchStatus deleted.
     * @param matchId Integer which refers to the Id of the certain match
     * @throws MatchNotFoundException when the certain match doesn't exist.
     */
    @PutMapping("{matchId}/delete")
    public void deleteMatch(@PathVariable int matchId) throws MatchNotFoundException {
        matchService.deleteMatch(matchId);
    }

    /**
     * PUT endpoint which finalizes the Status a certain match using the Id of this match.
     * @param matchId Integer which refers to the Id of the certain matching.
     * @throws MatchNotFoundException when the certain match doesn't exist.
     */
    @PutMapping("{matchId}/finalize")
    public void finalizeMatch(@PathVariable int matchId) throws MatchNotFoundException {
        matchService.finalizeMatch(matchId);
    }

    @PostMapping("createAutomaticMatches")
    public void automaticMatch() throws MatchException, JobOfferException {
        matchService.createAutomaticMatches();
    }

    @GetMapping("getProposedUnfinalizedMatches")
    public List<Match> viewProposedUnfinalizdMatches() {
        return matchService.viewProposedUnfinalizdMatches();
    }

    @GetMapping("getMostRecentMatches")
    public List<Match> getMostRecentMatches() {
        return matchService.getOrderedMatchesByDate();
    }


}
