package com.accenture.recrume.recruMe.controller;

import com.accenture.recrume.recruMe.dtos.ApplicantDto;
import com.accenture.recrume.recruMe.dtos.SkillDto;
import com.accenture.recrume.recruMe.exception.ApplicantException;
import com.accenture.recrume.recruMe.exception.ApplicantNotFoundException;
import com.accenture.recrume.recruMe.model.Applicant;
import com.accenture.recrume.recruMe.reader.ApplicantsReader;
import com.accenture.recrume.recruMe.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
//@RequestMapping("recruMe")
@RequestMapping("applicants")
public class ApplicantController {
    private ApplicantsReader applicantsReader;
    private ApplicantService applicantService;

    @Autowired
    public ApplicantController(ApplicantsReader applicantsReader, ApplicantService applicantService) {
        this.applicantsReader = applicantsReader;
        this.applicantService = applicantService;
    }

    @GetMapping
    public String welcome() {
        return "<b>Welcome to recruMe!</b>";
    }

    /**
     * GET endpoint which returns a json with the JobOffers imported from excel.
     * @return List of JobOffers
     * @throws IOException when the excel file wasn't found
     */
    @GetMapping("readExcel")
    public List<Applicant> getApplicants() throws IOException {
        return applicantsReader.readExcel("./src/main/resources/data for recrume.xlsx");
    }

    /**
     * GET endpoint that returns a Json with the applicants aged within a range of values. Years (from year - to year)
     * are read from the endpoint.
     * @param ageFrom Integer which refers to the one end of the year range, the earliest.
     * @param ageTo Integer which refers to the other end of the year range, the latest.
     * @return A list of applicant objects.
     */
    @GetMapping("age/from/{ageFrom}/to/{ageTo}")
    public List<Applicant> getApplicantsByAgeRange(@PathVariable int ageFrom, @PathVariable int ageTo) {
        return applicantService.getApplicantsByAgeRange(ageFrom, ageTo);
    }

    /**
     * POST endpoint which reads a Dto Applicant Object, add it in Applicant table and return it.
     * @param applicantDto Dto ApplicantObject (private String firstName; private String lastName;
     *                     private String address;private Region region; private int yob;
     *                     private EducationLevel educationLevel, ProfessionalLevel professionalLevel;
     *                     private List<Skill> skills;)
     *                     to get data from frontend for a new Applicant.
     * @return the new Applicant
     */
    @PostMapping("applicant/add")
    public Applicant addApplicant(@RequestBody ApplicantDto applicantDto){
        return applicantService.addApplicant(applicantDto);
    }

    /**
     * PUT endpoint which set a specific Applicant to inactive status.
     * @param id Integer which represents the id of a specific Applicant.
     */
    @PutMapping("applicant/{id}/inactive")
    public void setInactive(@PathVariable int id) throws ApplicantNotFoundException, ApplicantException {
        applicantService.setApplicantInactive(id);
    }

    /**
     * GET endpoint which returns Applicants for a specific region.
     * @param region String which represents the Region of the Applicants, read from path
     * @return List of Applicants
     */
    @GetMapping("region/{region}")
    public List<Applicant> getByRegion(@PathVariable String region) throws ApplicantNotFoundException {
        return applicantService.getByRegion(region);
    }

    @GetMapping("applicant/id/{id}")
    public Applicant getApplicant(@PathVariable int id) {
        return applicantService.getApplicantById(id);
    }

    /**
     * PUT endpoint which updates a specific Applicant, found by its id and update fields by Dto JobOffer Object.
     * @param applicantDto Dto Applicant Object(private String firstName; private String lastName;
     *                           private String address;private Region region; private int yob;
     *                           private EducationLevel educationLevel, ProfessionalLevel professionalLevel;)
     * to get data for update a Applicant.
     * @param id Integer which represents a specific existed Applicant.
     */
    @PutMapping("applicant/{id}/update")
    public void updateApplicant(@RequestBody ApplicantDto applicantDto,
                                      @PathVariable int id){
        applicantService.updateApplicant(id, applicantDto);
    }

    /**
     * Get Endpoint Return a list of Applicant which are from a specific Company.
     * @param lastName String which represents the lastName of a Applicant.
     * @return A list of Applicant.
     */
    @GetMapping("lastName/{lastName}")
    public List<Applicant> getByLastName(String lastName) {
        return applicantService.getApplicantByLastName(lastName);
    }

    /**
     * GET Endpoint which returns the existed active Applicants.
     * @return list of active Applicant objects
     */
    @GetMapping("activeApplicants")
    public List<Applicant> getActiveApplicants(){
        return applicantService.getActiveApplicants();
    }

    /**
     * Get Endpoint which reads a name of skill and return Applicant objects with the certain skill.
     * @param skillName String which refers to the skill name.
     * @return a list of Applicants with a certain skill.
     */
    @GetMapping("skill/{skillName}")
    public List<Applicant> getApplicantsBySkill(@PathVariable String skillName){
        return applicantService.getApplicantsBySkill(skillName);
    }

    /**
     * PUT endpoint which updates the skills of a specific Applicant, found
     * by its id and updates fields by Dto Skill Object.
     * @param skillDto Dto Skill Object (private String name) to get data for update a Applicant.
     * @param id Integer which represents a specific existed Applicant.
     * @param name String which represents the existed skill which is going to change.
     */
    @PutMapping("applicant/{id}/updateSkill/{name}")
    public void updateApplicantSkill(@RequestBody SkillDto skillDto,
                                    @PathVariable int id, @PathVariable String name){
        applicantService.updateApplicantSkill(skillDto, id, name);
    }


}