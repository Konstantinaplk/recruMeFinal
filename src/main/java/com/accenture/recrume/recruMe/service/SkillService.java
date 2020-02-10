package com.accenture.recrume.recruMe.service;

import com.accenture.recrume.recruMe.dtos.SkillDto;
import com.accenture.recrume.recruMe.model.JobSkill;
import com.accenture.recrume.recruMe.model.Skill;
import com.accenture.recrume.recruMe.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SkillService {
    private SkillsRepository skillRepo;

    @Autowired
    public SkillService(SkillsRepository skillRepo) {
        this.skillRepo = skillRepo;
    }

    /**
     * read one new skill from the frondend and store it in Skill table
     *
     * @param skillDto Dto object to get data from a new Skill
     * @return the skill stored in db
     */
    public Skill addSkill(SkillDto skillDto) {
        Skill skill = new Skill();
        skill.setName(skillDto.getName());
        return skillRepo.save(skill);
    }

    /**
     * read a name skill from the frondend and delete it from Skill table
     *
     * @param name String of the skill name
     */
    public void deleteSkill(String name) {
        Skill skill = new Skill();
        skill = skillRepo.findSkillByName(name);
        skillRepo.delete(skill);
    }

    /**
     * change the name of an existed skill
     *
     * @param id       integer skill id which will be updated
     * @param skillDto Dto with a new skill name
     * @return the updated skill
     */
    public Skill updateSkill(int id, SkillDto skillDto) {
        Skill skill = new Skill();
        skill = skillRepo.findById(id).get();
        skill.setName(skillDto.getName());
        return skillRepo.save(skill);
    }

    /**
     * get all skills of Skill Table
     * @return a list of Skills
     */
    public List<Skill> getAll() {
        return StreamSupport
                .stream(skillRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }


    /**
     * Check if a skill name exists in Skill Table, otherwise it saves it.
     * @param name Read a skillname
     */
    public void skillExist(String name) {
        Skill dbSkill = skillRepo.findSkillByName(name);
        if (dbSkill == null) {
            skillRepo.save(new Skill(name));
        }
    }
}
