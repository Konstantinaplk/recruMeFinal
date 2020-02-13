package com.accenture.recrume.recruMe.service;

import com.accenture.recrume.recruMe.dtos.ApplicantDto;
import com.accenture.recrume.recruMe.model.*;
import com.accenture.recrume.recruMe.repository.AppSkillsRepository;
import com.accenture.recrume.recruMe.repository.ApplicantsRepository;
import com.accenture.recrume.recruMe.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class ApplicantService {
    private ApplicantsRepository applicantsRepo;
    private AppSkillsRepository appSkillsRepo;
    private SkillService skillService;
    private SkillsRepository skillsRepo;

    @Autowired
    public ApplicantService(ApplicantsRepository applicantsRepo,
                            AppSkillsRepository appSkillsRepo,
                            SkillService skillService,
                            SkillsRepository skillsRepo) {
        this.applicantsRepo = applicantsRepo;
        this.appSkillsRepo = appSkillsRepo;
        this.skillService = skillService;
        this.skillsRepo = skillsRepo;
    }

    /**
     * Reads data from frontend to create a new Applicant Object to store in Applicant table.
     * and its skills are stored in AppSkill.
     *
     * @param applicantDto Dto Object to get data for a new Applicant.
     * @return Applicant just saved without its skills.
     */
    public Applicant addApplicant(ApplicantDto applicantDto) {
        Applicant applicant = new Applicant();
        applicant.setFirstName(applicantDto.getFirstName());
        applicant.setLastName(applicantDto.getLastName());
        applicant.setAddress(applicantDto.getAddress());
        applicant.setRegion(applicantDto.getRegion());
        applicant.setYob(applicantDto.getYob());
        applicant.setEducationLevel(applicantDto.getEducationLevel());
        applicant.setProfessionalLevel(applicantDto.getProfessionalLevel());
        applicantsRepo.save(applicant);
        for (Skill skill : applicantDto.getSkills()) {
            readApplicantSkill(skill, applicant);
        }
        return applicant;
    }

    /**
     * Reads from frontend a Applicant and one of its skills to store this match in Applicant table.
     * @param skill Read a skill.
     * @param applicant read a Applicant.
     */
    public void readApplicantSkill(Skill skill, Applicant applicant) {
        AppSkill appSkill = new AppSkill();
        appSkill.setApplicant(applicant);
        String name = skill.getName();
        skillService.skillExist(name);
        appSkill.setSkill(skillsRepo.findSkillByName(name));
        appSkillsRepo.save(appSkill);
    }

    /**
     * Returns a list of Applicants which are in the same Region.
     * @param region String to get the region.
     * @return A list of applicants.
     */
    public List<Applicant> getByRegion(String region) {
        return applicantsRepo.findByRegion(Region.valueOf(region).getValueToDb());
    }

    /**
     * Convert an active Applicant to inactive status/mode and saves it.
     * @param appId Integer which represent the id of a Applicant.
     */
    public void setApplicantInactive(int appId) {
        Applicant applicant = new Applicant();
        applicant = applicantsRepo.getApplicantById(appId);
        applicant.setStatus(Status.INACTIVE);
        applicantsRepo.save(applicant);
    }

    /**
     * Get the applicants with certain range of age.
     * @param ageFrom Integer which refers to the yob of the youngest applicant.
     * @param ageTo   Integer which refers to the yob of the oldest applicant.
     * @return A list of applicants
     */
    public List<Applicant> getApplicantsByAgeRange(int ageFrom, int ageTo) {
        int thisYear = GregorianCalendar.getInstance().get(Calendar.YEAR);
        int yearTo = thisYear - ageFrom;
        int yearFrom = thisYear - ageTo;
        return applicantsRepo.getByAgeRange(yearTo, yearFrom);
    }

    /**
     * Returns a Applicant with a specific id.
     * @param id Integer which represents a Applicant's id.
     * @return the Applicant with this id.
     */
    public Applicant getApplicantById(int id) {
        return applicantsRepo.getApplicantById(id);
    }

    /**
     * Reads data from frontend to update a Applicant Object and restore in it Applicant table.
     * @param id           Integer which represents a Applicant's id.
     * @param applicantDto Dto Applicant Object to get the updated fields.
     */
    public void updateApplicant(int id, ApplicantDto applicantDto) {
        Applicant applicant;
        applicant = applicantsRepo.getApplicantById(id);
        applicant.setFirstName(applicantDto.getFirstName());
        applicant.setLastName(applicantDto.getLastName());
        applicant.setAddress(applicantDto.getAddress());
        applicant.setRegion(applicantDto.getRegion());
        applicant.setYob(applicantDto.getYob());
        applicant.setEducationLevel(applicantDto.getEducationLevel());
        applicant.setProfessionalLevel(applicantDto.getProfessionalLevel());
        applicantsRepo.save(applicant);
    }

    /**
     * Return a list of applicants which are from a specific LastName.
     * @param lastName String which represents the LastName of a applicants.
     * @return A list of applicants.
     */
    public List<Applicant> getApplicantByLastName(String lastName) {
        return applicantsRepo.getApplicantByLastName(lastName);
    }


    /**
     * @return a list of Applicants which are Active in Status.
     */
    public List<Applicant> getActiveApplicants() {
        return applicantsRepo.findByStatus();
    }

    /**
     * Give a list of Applicants objects that has a certain skill.
     * @param skillName String which refers to the name of the skill.
     * @return A list of Applicants.
     */
    public List<Applicant> getApplicantsBySkill(String skillName) {
        Skill skill = skillsRepo.findSkillByName(skillName);
        List<Integer> applicantIdList = appSkillsRepo.getApplicantBySkill(skill.getId());
        List<Applicant> applicantList = new ArrayList<>();
        for (Integer applicantId : applicantIdList) {
            applicantList.add(applicantsRepo.findById(applicantId).get());
        }
        return applicantList;
    }



}
