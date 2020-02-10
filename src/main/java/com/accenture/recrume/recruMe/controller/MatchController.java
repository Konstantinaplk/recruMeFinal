package com.accenture.recrume.recruMe.controller;

import com.accenture.recrume.recruMe.dtos.MatchDto;
import com.accenture.recrume.recruMe.exception.MatchException;
import com.accenture.recrume.recruMe.exception.MatchNotFoundException;
import com.accenture.recrume.recruMe.repository.SkillsReader;
import com.accenture.recrume.recruMe.service.MatchService;
import com.accenture.recrume.recruMe.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("match")
public class MatchController {

    private MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService){
        this.matchService = matchService;

    }

    /**
     * POST endpoint which adds a manual, unfinalized match of an applicant and a jobOffer in Match table.
     * @param appId Integer which refers to an applicant's id.
     * @param jobOfferId Integer which refers to an jobOffer's id.
     */
    @PostMapping("appId/{appId}/jobOffer/{jobOfferId}")
    public void addManualMatch(@PathVariable int appId, @PathVariable int jobOfferId) throws MatchException {
        matchService.addManualMatch(appId, jobOfferId);
    }

    @PutMapping("{matchId}/delete")
    public void deleteMatch(@PathVariable int matchId) throws MatchNotFoundException {
        matchService.deleteMatch(matchId);
    }

    @PutMapping("{matchId}/finalize")
    public void finalizeMatch(@PathVariable int matchId) throws MatchNotFoundException {
        matchService.finalizeMatch(matchId);
    }

}
