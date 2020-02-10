package com.accenture.recrume.recruMe.repository;

import com.accenture.recrume.recruMe.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantsRepository<nativeQuery> extends JpaRepository<Applicant, Integer> {

    @Query(value = "select * from applicant where yob >= :yearFrom and yob <= :yearTo", nativeQuery = true)
    List<Applicant> getByAgeRange(@Param("yearTo") int yearTo, @Param("yearFrom") int yearFrom);

}

