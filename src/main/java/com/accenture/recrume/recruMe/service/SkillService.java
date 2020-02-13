package com.accenture.recrume.recruMe.service;

import com.accenture.recrume.recruMe.dtos.SkillDto;
import com.accenture.recrume.recruMe.model.AppSkill;
import com.accenture.recrume.recruMe.model.JobSkill;
import com.accenture.recrume.recruMe.model.Skill;
import com.accenture.recrume.recruMe.repository.AppSkillsRepository;
import com.accenture.recrume.recruMe.repository.JobSkillsRepository;
import com.accenture.recrume.recruMe.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SkillService {
    private SkillsRepository skillRepo;
    private JobSkillsRepository jobSkillsRepo;
    private AppSkillsRepository appSkillRepo;

    @Autowired
    public SkillService(SkillsRepository skillRepo, JobSkillsRepository jobSkillsRepo, AppSkillsRepository appSkillRepo) {
        this.skillRepo = skillRepo;
        this.jobSkillsRepo = jobSkillsRepo;
        this.appSkillRepo = appSkillRepo;
    }

    /**
     * Read one new skill from the frondend and store it in Skill table.
     *
     * @param skillDto Dto object to get data from a new Skill.
     * @return The skill stored in db.
     */
    public Skill addSkill(SkillDto skillDto) {
        Skill skill = new Skill();
        skill.setName(skillDto.getName());
        return skillRepo.save(skill);
    }

    /**
     * Read a name skill from the frondend and delete it from Skill table.
     *
     * @param name String of the skill name.
     */
    public void deleteSkill(String name) {
        Skill skill = new Skill();
        skill = skillRepo.findSkillByName(name);
        skillRepo.delete(skill);
    }

    /**
     * change the name of an existed skill with a certain skillDto object
     * which is read from the frondend.
     *
     * @param id       Integer skill id which will be updated.
     * @param skillDto Dto skillObject with a new skill name (field: int name).
     * @return The updated skill.
     */
    public Skill updateSkill(int id, SkillDto skillDto) {
        Skill skill = new Skill();
        skill = skillRepo.findById(id).get();
        skill.setName(skillDto.getName());
        return skillRepo.save(skill);
    }

    /**
     * Get all skills of Skill Table.
     *
     * @return A list of Skills.
     */
    public List<Skill> getAll() {
        return StreamSupport
                .stream(skillRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Check if a skill name exists in Skill Table, otherwise it saves it.
     *
     * @param name String which refers to a certain skillName.
     */
    public void skillExist(String name) {
        Skill dbSkill = skillRepo.findSkillByName(name);
        if (dbSkill == null) {
            skillRepo.save(new Skill(name));
        }
    }

    private int max;
    private Skill topSkill;

    private Skill chechSkill(int skillId) {
        int i = 0;
        for (JobSkill jobSkill : jobSkillsRepo.findAll()) {
            if (jobSkill.getSkill().getId() == skillId) {
                i += 1;
            }
        }
        if (i > max) {
            max = i;
            topSkill = skillRepo.findById(skillId).get();

        }
        return topSkill;
    }

    /**
     * Retrieve the list of the 20 most requested and offered skills.
     *
     * @return
     */
    public List<Skill> getTopRequestedSkills() {
        max = 0;
        List<Skill> sortedskillsByRequesting = new ArrayList<>();
        List<Skill> skillsId = StreamSupport
                .stream(skillRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
        for (Skill skillsUpdated : skillsId) {
            max = 0;
            if (!sortedskillsByRequesting.contains(skillsUpdated)) {
                for (Skill skill : skillsId) {
                    if (!sortedskillsByRequesting.contains(skill)) {
                        chechSkill(skill.getId());
                    }
                }
                sortedskillsByRequesting.add(topSkill);
            }
        }
        return sortedskillsByRequesting.subList(0,3);
    }

    /**
     * Retrieve the list of the not matched skills by the applicants
     * @return
     */
    public List<Skill> notMatchedSkillsByTheApplicants(){
        List<Skill> notMatcedSkills = new ArrayList<>();
        for(Skill skill: skillRepo.findAll()){
            AppSkill appSkill  = appSkillRepo.skillExists(skill.getId());
            if(appSkill == null){
                notMatcedSkills.add(skill);
            }
        }
        return notMatcedSkills;
    }

}
