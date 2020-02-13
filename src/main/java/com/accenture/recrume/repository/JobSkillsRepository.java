package com.accenture.recrume.repository;

import com.accenture.recrume.model.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSkillsRepository extends JpaRepository<JobSkill, Integer> {

    @Query(value = "select * from job_skill where job_offer_id =:idJobOffer and skill_id =:idSkill", nativeQuery = true)
    JobSkill getJobSkill(@Param("idJobOffer") int idJobOffer, @Param("idSkill") int idSkill);

    @Query(value = "select job_offer_id from job_skill where skill_id=:idSkill", nativeQuery = true)
    List<Integer> getJobOfferBySkill(@Param("idSkill") int idSkill);

    @Query(value = "select skill_id from job_skill where job_offer_id = :jobId", nativeQuery = true)
    List<Integer> getSkillsByJobId(@Param("jobId") int jobId);
}
