package com.accenture.recrume.repository;

import com.accenture.recrume.model.AppSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppSkillsRepository extends JpaRepository<AppSkill, Integer> {

    @Query(value = "select * from app_skill where applicant_id = :appId and skill_id = :skillId", nativeQuery =true)
    AppSkill  ApplicantHasSkill(@Param("appId") int appId, @Param("skillId") int skillId);

    @Query (value = "select top 1 * from app_skill where skill_id=:skillId", nativeQuery = true)
    AppSkill skillExists(@Param("skillId") int skillId);

    @Query(value = "select applicant_id from app_skill where skill_id=:idSkill", nativeQuery = true)
    List<Integer> getApplicantBySkill(@Param("idSkill") int id);

    @Query(value = "select * from app_skill where applicant_id =:idApp and skill_id =:idSkill", nativeQuery = true)
    AppSkill getAppSkill(@Param("idApp") int id, @Param("idSkill") int id1);
}
