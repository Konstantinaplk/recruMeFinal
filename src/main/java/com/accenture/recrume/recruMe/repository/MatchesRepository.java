package com.accenture.recrume.recruMe.repository;

import com.accenture.recrume.recruMe.model.Match;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MatchesRepository extends PagingAndSortingRepository<Match, Integer> {
}
