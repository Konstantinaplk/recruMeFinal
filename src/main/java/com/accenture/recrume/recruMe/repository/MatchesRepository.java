package com.accenture.recrume.recruMe.repository;

import com.accenture.recrume.recruMe.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Repository
public interface MatchesRepository extends JpaRepository<Match, Integer> {

    @Query (value = "select * from Match where id=:matchId", nativeQuery = true)
    Match getMatchById(@Param("matchId") int matchId);

    @Query (value = "select * from Match where match_status=0", nativeQuery = true)
    List<Match> getAutomaticUnfinalizedMatches();

    @Query (value = "select * from Match where match_status = 1 order by date_submitted desc", nativeQuery = true)
    List<Match> getOrderedMatchesByDate();

    @Query (value = "select * from Match where applicant_id=:appId and match_status!=2", nativeQuery = true)
    Match getMatchByAppId(@Param("appId") int appId);
}
