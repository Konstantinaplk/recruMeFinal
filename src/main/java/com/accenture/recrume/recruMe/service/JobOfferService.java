package com.accenture.recrume.recruMe.service;

import com.accenture.recrume.recruMe.dtos.JobOfferDto;
import com.accenture.recrume.recruMe.dtos.SkillDto;
import com.accenture.recrume.recruMe.model.*;
import com.accenture.recrume.recruMe.repository.JobOffersRepository;
import com.accenture.recrume.recruMe.repository.JobSkillsRepository;
import com.accenture.recrume.recruMe.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class JobOfferService {
    @Autowired
    private JobOffersRepository jobOffersRepo;
    @Autowired
    private JobSkillsRepository jobSkillsRepo;
    @Autowired
    private SkillsRepository skillsRepo;

    public JobOffer addJobOffer(JobOfferDto jobOfferDto) {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setCompany(jobOfferDto.getCompany());
        jobOffer.setTitle(jobOfferDto.getTitle());
        jobOffer.setRegion(jobOfferDto.getRegion());
        jobOffer.setStatus(Status.ACTIVE);
        jobOffer.setEducationLevel((jobOfferDto.getEducationLevel()));
        jobOffersRepo.save(jobOffer);
        readJobOfferSkills(jobOfferDto, jobOffer);
        return jobOffer;
    }

    public void readJobOfferSkills(JobOfferDto jobOfferDto, JobOffer jobOffer){
        for (Skill skill : jobOfferDto.getSkills()) {
            JobSkill jobSkill = new JobSkill();
            jobSkill.setJobOfferId(jobOffer);
            Skill dbSkill = skillsRepo.findSkillByName(skill.getName());
            if (dbSkill == null) {
                skillsRepo.save(skill);
                jobSkill.setSkillId(skill);
            } else {
                jobSkill.setSkillId(dbSkill);
            }
            jobSkillsRepo.save(jobSkill);
        }
    }

    public List<JobOffer> getByRegion(String region) {
        return jobOffersRepo.findByRegion(Region.valueOf(region).getValueToDb());
    }

    public void setInactive(int id) {
        JobOffer jobOffer = new JobOffer();
        jobOffer = jobOffersRepo.findById(id).get();
        jobOffer.setStatus(Status.INACTIVE);
        jobOffersRepo.save(jobOffer);
    }

    public JobOffer getJobOffer(int id) {
        return jobOffersRepo.findById(id).get();
    }

    public JobOffer updateJobOffer(int id, JobOfferDto jobOfferDto) {
        JobOffer jobOffer;
        jobOffer = jobOffersRepo.findById(id).get();
        jobOffer.setCompany(jobOfferDto.getCompany());
        jobOffer.setStatus(jobOffer.getStatus());
        jobOffer.setEducationLevel(jobOfferDto.getEducationLevel());
        jobOffer.setRegion(jobOfferDto.getRegion());
        jobOffer.setDateSubmitted(jobOfferDto.getDateSubmitted());
        jobOffer.setTitle(jobOfferDto.getTitle());

        return jobOffersRepo.save(jobOffer);
    }

    public void updateJobOfferSkill(SkillDto skillDto, int id, String name){
        Skill skill = skillsRepo.findSkillByName(name);
        JobSkill jobSkill = jobSkillsRepo.getJobSkill(id, skill.getId());
        Skill skilldto = skillsRepo.findSkillByName(skillDto.getName());

        jobSkill.setSkillId(skilldto);
        jobSkillsRepo.save(jobSkill);
    }

    public List<JobOffer> getByCompany(String company) {
        return jobOffersRepo.findByCompany(company);
    }

    public List<JobOffer> getActiveJobOffers() {
        return jobOffersRepo.findByStatus();
    }

    public List<JobOffer> getJobOfferByDate(int day, int month, int year) {
        long today = GregorianCalendar.getInstance().getTimeInMillis();
        System.out.println(today);
        System.out.println("============================hgh");
        Calendar certainDay = GregorianCalendar.getInstance();
        certainDay.set(Calendar.YEAR, year);
        certainDay.set(Calendar.MONTH, month);
        certainDay.set(Calendar.DATE, day);

        long certainDate = certainDay.getTimeInMillis();
        return jobOffersRepo.findByDate(today, certainDate);

    }
}
