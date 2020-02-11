package com.accenture.recrume.recruMe.service;

import com.accenture.recrume.recruMe.dtos.JobOfferDto;
import com.accenture.recrume.recruMe.dtos.SkillDto;
import com.accenture.recrume.recruMe.model.*;
import com.accenture.recrume.recruMe.repository.JobOffersRepository;
import com.accenture.recrume.recruMe.repository.JobSkillsRepository;
import com.accenture.recrume.recruMe.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class JobOfferService {
    private JobOffersRepository jobOffersRepo;
    private JobSkillsRepository jobSkillsRepo;
    private SkillsRepository skillsRepo;
    private SkillService skillService;


    @Autowired
    public JobOfferService(JobOffersRepository jobOffersRepo,
                           JobSkillsRepository jobSkillsRepo,
                           SkillsRepository skillsRepo,
                           SkillService skillService) {
        this.jobOffersRepo = jobOffersRepo;
        this.jobSkillsRepo = jobSkillsRepo;
        this.skillsRepo = skillsRepo;
        this.skillService = skillService;
    }

     /**
     * Reads data from frontend to create a new JobOffer Object and its skills to store in JobSkill table.
     * @param jobOfferDto Dto Object to get data for a new JobOffer.
     * @return Job offer just saved without its skills.
     */
    public JobOffer addJobOffer(JobOfferDto jobOfferDto) {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setCompany(jobOfferDto.getCompany());
        jobOffer.setTitle(jobOfferDto.getTitle());
        jobOffer.setRegion(jobOfferDto.getRegion());
        jobOffer.setStatus(Status.ACTIVE);
        jobOffer.setEducationLevel((jobOfferDto.getEducationLevel()));
        jobOffer.setProfessionalLevel(jobOfferDto.getProfessionalLevel());
        jobOffersRepo.save(jobOffer);
        for (Skill skill : jobOfferDto.getSkills()) {
            readJobOfferSkill(skill, jobOffer);
        }
        return jobOffer;
    }

    /**
     * Reads from frontend a JobOffer and one of its skills to store this match in JobSkill table.
     * @param skill Read a skill.
     * @param jobOffer read a JobOffer.
     */
    public void readJobOfferSkill(Skill skill, JobOffer jobOffer){
            JobSkill jobSkill = new JobSkill();
            jobSkill.setJobOffer(jobOffer);
            String name = skill.getName();
            skillService.skillExist(name);
            jobSkill.setSkill(skillsRepo.findSkillByName(name));
            jobSkillsRepo.save(jobSkill);
    }

    /**
     * Returns a list of JobOffers which are in the same Region.
     * @param region String to get the region.
     * @return
     */
    public List<JobOffer> getByRegion(String region) {
        return jobOffersRepo.findByRegion(Region.valueOf(region).getValueToDb());
    }

    /**
     * Convert an active Job offer to inactive status/mode and saves it.
     * @param id Integer which represent the id of a JobOffer.
     */
    public void setJobOfferInactive(int id) {
        JobOffer jobOffer = new JobOffer();
        jobOffer = jobOffersRepo.findById(id);
        jobOffer.setStatus(Status.INACTIVE);
        jobOffersRepo.save(jobOffer);
    }

    /**
     * Returns a JobOffer with a specific id.
     * @param id Integer which represents a JobOffer's id.
     * @return the JobOffer with this id
     */
    public JobOffer getJobOffer(int id) {
        return jobOffersRepo.findById(id);
    }

    /**
     * Reads data from frontend to update a JobOffer Object and restore in it JobOffer table.
     * @param id Integer which represents a JobOffer's id.
     * @param jobOfferDto Dto JobOffer Object to get the updated fields.
     * @return the updated JobOffer
     */
    public JobOffer updateJobOffer(int id, JobOfferDto jobOfferDto) {
        JobOffer jobOffer;
        jobOffer = jobOffersRepo.findById(id);
        jobOffer.setCompany(jobOfferDto.getCompany());
        jobOffer.setStatus(jobOffer.getStatus());
        jobOffer.setEducationLevel(jobOfferDto.getEducationLevel());
        jobOffer.setProfessionalLevel(jobOfferDto.getProfessionalLevel());
        jobOffer.setRegion(jobOfferDto.getRegion());
        jobOffer.setDateSubmitted(jobOfferDto.getDateSubmitted());
        jobOffer.setTitle(jobOfferDto.getTitle());

        return jobOffersRepo.save(jobOffer);
    }

    /**
     * Reads an other Skill from frontend to update a JobSkill Object and restore in it JobSkill table.
     * @param skillDto Dto Skill Object to get the new Skill to replace the old.
     * @param id Integer which represents the id of a JobOffer
     * @param name The name of the existing Skill, which is going to change.
     */
    public void updateJobOfferSkill(SkillDto skillDto, int id, String name) {
        Skill skill = skillsRepo.findSkillByName(name);
        JobSkill jobSkill = jobSkillsRepo.getJobSkill(id, skill.getId());
        skillService.skillExist(skillDto.getName());
        jobSkill.setSkill(skillsRepo.findSkillByName(skillDto.getName()));
        jobSkillsRepo.save(jobSkill);
    }

    /**
     * Return a list of JobOffer which are from a specific Company.
     * @param company String which represents the Company Name of a JobOffer.
     * @return A list of JobOffer
     */
    public List<JobOffer> getByCompany(String company) {
        return jobOffersRepo.findByCompany(company);
    }

    /**
     * @return a list of JobOffer which are Active in Status.
     */
    public List<JobOffer> getActiveJobOffers() {
        return jobOffersRepo.findByStatus();
    }

    /**
     * Give a list of JobOffer which submitted after a specific date until today.
     * @param day Integer which represents the day of the month for the date.
     * @param month Integer which represents the month of the year for the date.
     * @param year Integer which represents the year for the date.
     * @return A list of JobOffer.
     */
    public List<JobOffer> getJobOfferByDate(int day, int month, int year) {
        long today = GregorianCalendar.getInstance().getTimeInMillis();
        Calendar certainDay = GregorianCalendar.getInstance();
        certainDay.set(Calendar.YEAR, year);
        certainDay.set(Calendar.MONTH, month);
        certainDay.set(Calendar.DATE, day);

        long certainDate = certainDay.getTimeInMillis();
        return jobOffersRepo.findByDate(today, certainDate);
    }

    /**
     * Give a list of JobOffer objects that has a certain skill.
     * @param skillName String which refers to the name of the skill.
     * @return A list of JobOffers.
     */
    public List<JobOffer> getJobOfferBySkill(String skillName) {
        Skill skill = skillsRepo.findSkillByName(skillName);
        List<Integer> jobOfferIdList = jobSkillsRepo.getJobOfferBySkill(skill.getId());
        List<JobOffer> jobOfferList = new ArrayList<>();
        for (Integer jobOfferId:jobOfferIdList){
            jobOfferList.add(jobOffersRepo.findById(jobOfferId).get());
        }
        return jobOfferList;
    }
}
