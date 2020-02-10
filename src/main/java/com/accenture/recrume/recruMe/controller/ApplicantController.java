package com.accenture.recrume.recruMe.controller;


import com.accenture.recrume.recruMe.model.Applicant;
import com.accenture.recrume.recruMe.repository.ApplicantsReader;
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
    @Autowired
    private ApplicantsReader applicantsReader;
    @Autowired
    private ApplicantService applicantService;

    @GetMapping
    public String welcome(){
        return "<b>Welcome to recruMe!</b>";
    }

    @GetMapping("all")
    public List<Applicant> getApplicants() throws IOException {

        return applicantsReader.readExcel("applicants.xlsx");
    }

    @GetMapping("age/from/{ageFrom}/to/{ageTo}")
    public List<Applicant> getApplicantsByAgeRange(@PathVariable int ageFrom, @PathVariable int ageTo) {
        return applicantService.getApplicantsByAgeRange(ageFrom, ageTo);
    }

}