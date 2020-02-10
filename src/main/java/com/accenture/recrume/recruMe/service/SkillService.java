package com.accenture.recrume.recruMe.service;

import com.accenture.recrume.recruMe.dtos.SkillDto;
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
    public SkillService(SkillsRepository skillRepo){
        this.skillRepo = skillRepo;
    }

    public Skill addSkill(SkillDto skillDto) {
        Skill skill = new Skill();
        skill.setName(skillDto.getName());
        return skillRepo.save(skill);
    }

    public void deleteSkill(String name) {
        Skill skill = new Skill();
        skill = skillRepo.findSkillByName(name);
        skillRepo.delete(skill);
    }

    public Skill updateSkill(int id, SkillDto skillDto) {
        Skill skill = new Skill();
        skill = skillRepo.findById(id).get();
        skill.setName(skillDto.getName());
        return skillRepo.save(skill);
    }

    public List<Skill> getAll(){
        return StreamSupport
                .stream(skillRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
