package com.accenture.recrume.recruMe.repository;

import com.accenture.recrume.recruMe.model.Match;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;


@Repository
public interface MatchesRepository extends PagingAndSortingRepository<Match, Integer> {

    @Query (value = "select * from Match where id=:matchId", nativeQuery = true)
    Match getMatchById(@Param("matchId") int matchId);


}
