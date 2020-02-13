package com.accenture.recrume.recruMe.service;

import com.accenture.recrume.recruMe.dtos.ApplicantDto;
import com.accenture.recrume.recruMe.exception.ApplicantNotFoundException;
import com.accenture.recrume.recruMe.model.*;
import com.accenture.recrume.recruMe.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ApplicantServiceTest {
    private Applicant applicant;
    private ApplicantDto applicantDto;

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private ApplicantsRepository applicantsRepo;

    @Autowired
    private AppSkillsRepository appSkillsRepo;
    @Autowired
    private SkillService skillService;
    @Autowired
    private SkillsRepository skillsRepo;

    @BeforeEach
    void setUp() {
        applicant = new Applicant();
        applicant.setLastName("Papadopoulou");
        applicant.setFirstName("Anna");
        applicant.setStatus(Status.ACTIVE);
        applicant.setYob(1990);
        applicant.setRegion(Region.AEGEAN);
        applicant.setProfessionalLevel(ProfessionalLevel.ENTRY);
        applicant.setEducationLevel(EducationLevel.MASTER);
        applicant.setAddress("Unknown");

        applicantDto = new ApplicantDto();
        applicantDto.setLastName("Papadopoulou");
        applicantDto.setFirstName("Anna");
        applicantDto.setYob(1990);
        applicantDto.setRegion(Region.AEGEAN);
        applicantDto.setProfessionalLevel(ProfessionalLevel.ENTRY);
        applicantDto.setEducationLevel(EducationLevel.MASTER);
        applicantDto.setAddress("Unknown");
        applicantDto.setSkills(new ArrayList<>());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addApplicant() {
        assertEquals(applicant.getLastName(), applicantService.addApplicant(applicantDto).getLastName());
    }

    @Test
    public void getByRegion() throws ApplicantNotFoundException {
        assertEquals(1,applicantService.getByRegion("AEGEAN").size());
    }

    @Test
    public void getApplicantById(){
        Applicant ap1 = applicantsRepo.getApplicantById(9);
        assertEquals("Dimou",applicantService.getApplicantById(ap1.getId()).getLastName());
    }

    @Test
    public void getApplicantsBySkill(){
        assertEquals(3, applicantService.getApplicantsBySkill("Problem-solving").size());
    }
}