package com.accenture.recrume.recruMe.controller;

import com.accenture.recrume.recruMe.dtos.JobOfferDto;
import com.accenture.recrume.recruMe.dtos.SkillDto;
import com.accenture.recrume.recruMe.model.JobOffer;
import com.accenture.recrume.recruMe.reader.JobOffersReader;
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

    /**
     *GET end point which returns a json with the JobOffers imported from excel
     * @return List of JobOffers
     * @throws IOException when the excel file wasn't found
     */
    @GetMapping("readExcel")
    public List<JobOffer> getJobOffers() throws IOException {

        return jobOffersReader.readExcel("./src/main/resources/data for recrume.xlsx");
    }

    /**
     * POST endpoint which reads a Dto JobOffer Object, add it in JobOffer table and return it.
     * @param jobOfferDto Dto JobOfferObject (private String company; private Region region; private String title;
     * private EducationLevel educationLevel; private long dateSubmitted; private Status status;
     * private List<Skill> skills;) to get data for a new JobOffer.
     * @return the new JobOffer
     */
    @PostMapping("add")
    public JobOffer addJobOffer(@RequestBody JobOfferDto jobOfferDto){
        return jobOfferService.addJobOffer(jobOfferDto);
    }

    /**
     * GET endpoint which returns JobOffers for a specific region.
     * @param region String which represents the Region of the JobOffers, read from path
     * @return List of JobOffers
     */
    @GetMapping("region/{region}")
    public List<JobOffer> getByRegion(@PathVariable String region){
        return jobOfferService.getByRegion(region);
    }

    /**
     * PUT endpoint which set a specific JobOffer to inactive status.
     * @param id Integer which represents the id of a specific JobOffer.
     */
    @PutMapping("{id}/inactive")
    public void setInactive(@PathVariable int id){
        jobOfferService.setJobOfferInactive(id);
    }

    /**
     * GET endpoint which returns a specific JobOffer by its id
     * @param id Integer which represents the id of a specific existed JobOffer
     * @return A JobOfferObject
     */
    @GetMapping("id/{id}")
    public JobOffer getJobOffer(@PathVariable int id) {
        return jobOfferService.getJobOfferById(id);
    }

    /**
     * PUT endpoint which updates a specific JobOffer, found by its id and update fields by Dto JobOffer Object.
     * @param jobOfferDto Dto JobOffer Object (private String company; private Region region; private String title;
     * private EducationLevel educationLevel; private long dateSubmitted; private Status status)
     * to get data for update a JobOffer.
     * @param id Integer which represents a specific existed JobOffer.
     * @return
     */
    @PutMapping("{id}/update")
    public JobOffer updateJobOffer(@RequestBody JobOfferDto jobOfferDto,
                                   @PathVariable int id){
        return jobOfferService.updateJobOffer(id, jobOfferDto);
    }

    /**
     * PUT endpoint which updates the skills of a specific JobOffer, found by its id and updates fields by Dto Skill Object.
     * @param skillDto Dto Skill Object (private String name) to get data for update a JobOffer.
     * @param id Integer which represents a specific existed JobOffer.
     * @param name String which represents the existed skill which is going to change.
     */
    @PutMapping("jobOffer/{id}/updateSkill/{name}")
    public void updateJobOfferSkill(@RequestBody SkillDto skillDto,
                                    @PathVariable int id, @PathVariable String name){
        jobOfferService.updateJobOfferSkill(skillDto, id, name);
    }

    /**
     * GET endpoint which returns a list of JobOffers from a specific existing company.
     * @param company String which represents a company name.
     * @return List of JobOffers from a specific company.
     */
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

    /**
     * Give a list of JobOffer which submitted after a specific date until today.
     * @param day Integer which represents the day of the month for the date, read from path.
     * @param month Integer which represents the month of the year for the date, read from path.
     * @param year Integer which represents the year for the date, read from path.
     * @return A list of jobOffer objects.
     */
    @GetMapping("date/day/{day}/month/{month}/year/{year}")
    public List<JobOffer> getJobOffersByDate(@PathVariable int day, @PathVariable int month, @PathVariable int year){
        return jobOfferService.getJobOfferByDate(day, month, year);
    }

    /**
     * Get Endpoint which reads a name of skill and return JobOffer objects with the certain skill.
     * @param skillName String which refers to the skill name.
     * @return a list of JobOffers with a certain skill.
     */
    @GetMapping("skill/{skillName}")
    public List<JobOffer> getJobOffersBySkill(@PathVariable String skillName){
        return jobOfferService.getJobOfferBySkill(skillName);
    }
}
