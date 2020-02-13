package com.accenture.recrume.repository;

import com.accenture.recrume.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantsRepository extends JpaRepository<Applicant, Integer> {

    @Query(value = "select * from applicant where yob >= :yearFrom and yob <= :yearTo", nativeQuery = true)
    List<Applicant> getByAgeRange(@Param("yearTo") int yearTo, @Param("yearFrom") int yearFrom);

    @Query(value = "select * from applicant where id =:appId", nativeQuery = true)
    Applicant getApplicantById(@Param("appId") int appId);

    @Query(value = "select id from applicant", nativeQuery = true)
    List<Integer> getIdAll();

    @Query(value = "select id from applicant where status = 1", nativeQuery = true)
    List<Integer> getActiveApplicants();

    @Query(value = "select * from applicant where region = :region", nativeQuery = true)
    List<Applicant> findByRegion(@Param("region") int valueToDb);

    @Query(value = "select * from applicant where last_name=:lastName", nativeQuery = true)
    List<Applicant> getApplicantByLastName(@Param("lastName") String lastName);

    @Query(value = "select * from applicant where status=1", nativeQuery = true)
    List<Applicant> findByStatus();
}

