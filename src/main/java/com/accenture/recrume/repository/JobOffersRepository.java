package com.accenture.recrume.repository;

import com.accenture.recrume.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOffersRepository extends JpaRepository<JobOffer, Integer> {

    @Query(value = "select * from job_offer where region = :region", nativeQuery = true)
    public List<JobOffer> findByRegion(@Param("region") int region);

    @Query(value = "select * from job_offer where company = :company",nativeQuery = true)
    List<JobOffer> findByCompany(@Param("company") String company);

    @Query(value = "select * from job_offer where status = 1",nativeQuery = true)
    List<JobOffer> findByStatus();

    @Query(value = "select * from job_offer where date_submitted <= :today and date_submitted > :certainDate", nativeQuery = true)
    List<JobOffer> findByDate(@Param("today") long today, @Param("certainDate") long certainDate);

    @Query(value = "select * from job_offer where id=:jobOfferId", nativeQuery = true)
    JobOffer findById(@Param("jobOfferId") int jobOfferId);

    @Query(value = "select id from job_offer where status = 1", nativeQuery = true)
    List<Integer> getActiveJobOfferId();
}
