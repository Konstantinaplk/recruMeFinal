package com.accenture.recrume.recruMe.controller;


import com.accenture.recrume.recruMe.model.Applicant;
import com.accenture.recrume.recruMe.reader.ApplicantsReader;
import com.accenture.recrume.recruMe.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}