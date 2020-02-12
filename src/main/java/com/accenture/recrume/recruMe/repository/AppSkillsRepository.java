package com.accenture.recrume.recruMe.repository;

import com.accenture.recrume.recruMe.model.AppSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppSkillsRepository extends PagingAndSortingRepository<AppSkill, Integer> {

    @Query(value = "select * from app_skill where applicant_id = :appId and skill_id = :skillId", nativeQuery =true)
    AppSkill  ApplicantHasSkill(@Param("appId") int appId, @Param("skillId") int skillId);
}
