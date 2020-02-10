package com.accenture.recrume.recruMe.controller;

import com.accenture.recrume.recruMe.dtos.JobOfferDto;
import com.accenture.recrume.recruMe.dtos.SkillDto;
import com.accenture.recrume.recruMe.model.JobOffer;
import com.accenture.recrume.recruMe.repository.JobOffersReader;
import com.accenture.recrume.recruMe.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("jobOffer")
public class JobOfferController {
    private JobOfferService jobOfferService;
    private JobOffersReader jobOffersReader;

    @Autowired
    public JobOfferController(JobOfferService jobOfferService, JobOffersReader jobOffersReader) {
        this.jobOfferService = jobOfferService;
        this.jobOffersReader = jobOffersReader;
    }

    @GetMapping("readExcel")
    public List<JobOffer> getJobOffers() throws IOException {

        return jobOffersReader.readExcel("jobOffers.xlsx");
    }

    @PostMapping("add")
    public JobOffer addJobOffer(@RequestBody JobOfferDto jobOfferDto){
        return jobOfferService.addJobOffer(jobOfferDto);
    }

    @GetMapping("region/{region}")
    public List<JobOffer> getByRegion(@PathVariable String region){
        return jobOfferService.getByRegion(region);
    }

    @PutMapping("{id}/inactive")
    public void setInactive(@PathVariable int id){
        jobOfferService.setInactive(id);
    }

    @GetMapping("id/{id}")
    public JobOffer getJobOffer(@PathVariable int id) {
        return jobOfferService.getJobOffer(id);
    }

    @PutMapping("{id}/update")
    public JobOffer updateJobOffer(@RequestBody JobOfferDto jobOfferDto,
                                   @PathVariable int id){
        return jobOfferService.updateJobOffer(id, jobOfferDto);
    }

    @GetMapping("company/{company}")
    public List<JobOffer> getCompany(@PathVariable String company){
        return jobOfferService.getByCompany(company
        );
    }

    /**
     * GET Endpoint which returns the existed active JobOffers.
     * @return list of active JobOffer objects
     */
    @GetMapping("activeJobOffers")
    public List<JobOffer> getActiveJobOffers(){
        return jobOfferService.getActiveJobOffers();
    }

    @GetMapping("date/day/{day}/month/{month}/year/{year}")
    public List<JobOffer> getJobOffersByDate(@PathVariable int day, @PathVariable int month, @PathVariable int year){
        return jobOfferService.getJobOfferByDate(day, month, year);
    };

    @PutMapping("jobOffer/{id}/updateSkill/{name}")
    public void updateJobOfferSkill(@RequestBody SkillDto skillDto,
                                    @PathVariable int id, @PathVariable String name){
        jobOfferService.updateJobOfferSkill(skillDto, id, name);
    }

}
