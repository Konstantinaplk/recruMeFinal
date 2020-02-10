package com.accenture.recrume.recruMe.repository;

import com.accenture.recrume.recruMe.model.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends CrudRepository<Skill, Integer> {

    @Query(value = "select * from skill where (name = :name)", nativeQuery = true)
    public Skill findSkillByName(@Param("name") String name);
}
