package com.accenture.recrume.recruMe.controller;

import com.accenture.recrume.recruMe.dtos.SkillDto;
import com.accenture.recrume.recruMe.model.Skill;
import com.accenture.recrume.recruMe.reader.SkillsReader;
import com.accenture.recrume.recruMe.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("skills")
public class SkillController {
    private SkillService skillService;
    private SkillsReader skillsReader;

    @Autowired
    public SkillController(SkillService skillService, SkillsReader skillsReader){
        this.skillService = skillService;
        this.skillsReader = skillsReader;
    }

    /**
     *GET Endpoint which returns a json with the skills imported from excel.
     * @return list of skills
     * @throws IOException when the excel file wasn't found
     */
    @GetMapping("readExcel")
    public List<String> getSkills() throws IOException {
        return skillsReader.readExcel("./src/main/resources/data for recrume.xlsx");
    }

    /**
     *POST Endpoint which reads a DtoSkill object and add it in Skill Table.
     * @param skillDto Dto SkillObject (fields: String name)
     *                 to get data from a new Skill.
     */
    @PostMapping("skill/add")
    public void addSkill(@RequestBody SkillDto skillDto){
        skillService.addSkill(skillDto);
    }

    /**
     * Post Endpoint which reads a String name of an existed skill and
     * deleted from the Skill Table.
     * @param name String which represents the name of a skill
     */
    @PostMapping("skill/delete")
    public void deleteSkill(@RequestBody String name){
        skillService.deleteSkill(name);
    }

    /**
     * PUT Endpoint which reads the id of an existed skill and update its name
     * using the inserted body Dto Skill object.
     * @param id Integer which represents the id of an existed skill
     * @param skillDto which represents a Dto skillObject (fields: name)
     */
    @PutMapping("skill/{id}")
    public void updateSkill(@PathVariable int id,
                            @RequestBody SkillDto skillDto){
        skillService.updateSkill(id, skillDto);
    }

    /**
     * GET Endpoint which get all the skill stored in Skill Table
     * @return list of all stored skills
     */
    @GetMapping("all")
    public List<Skill> getAllSkills(){
        return skillService.getAll();
    }

    @GetMapping("mostRequested")
    public List<Skill> getmostRequestedSkill(){
        return skillService.getTopRequestedSkills();
    }

    @GetMapping("notMatchedSkillsByTheApplicants")
    public List<Skill> notMatchedSkillsByTheApplicants(){
        return skillService.notMatchedSkillsByTheApplicants();
    }
}
