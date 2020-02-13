package com.accenture.recrume.repository;

import com.accenture.recrume.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends JpaRepository<Skill, Integer> {

    @Query(value = "select * from skill where (name = :name)", nativeQuery = true)
    public Skill findSkillByName(@Param("name") String name);
}
