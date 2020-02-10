package com.accenture.recrume.recruMe.repository;

import com.accenture.recrume.recruMe.model.JobSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSkillsRepository extends CrudRepository<JobSkill, Integer> {

    @Query(value = "select * from job_skill where job_offer_id_id =:idJobOffer and skill_id_id =:idSkill", nativeQuery = true)
    JobSkill getJobSkill(@Param("idJobOffer") int idJobOffer, @Param("idSkill") int idSkill);

}
