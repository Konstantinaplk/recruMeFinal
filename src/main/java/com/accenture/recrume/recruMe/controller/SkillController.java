package com.accenture.recrume.recruMe.controller;

import com.accenture.recrume.recruMe.dtos.SkillDto;
import com.accenture.recrume.recruMe.model.Skill;
import com.accenture.recrume.recruMe.repository.SkillsReader;
import com.accenture.recrume.recruMe.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("skill")
public class SkillController {

    @Autowired
    private SkillService skillService;
    @Autowired
    private SkillsReader skillsReader;

    @GetMapping("readExcel")
    public List<Skill> getSkills() throws IOException {
        return skillsReader.readExcel("skills.xlsx");
    }

    @PostMapping("add")
    public void addSkill(@RequestBody SkillDto skillDto){
        skillService.addSkill(skillDto);
    }

    @PostMapping("delete")
    public void deleteSkill(@RequestBody String name){
        skillService.deleteSkill(name);
    }

    @PutMapping("{id}")
    public void updateSkill(@PathVariable int id,
                            @RequestBody SkillDto skillDto){
        skillService.updateSkill(id, skillDto);
    }

    @GetMapping("all")
    public List<Skill> getAllSkills(){
        return skillService.getAll();
    }
}
