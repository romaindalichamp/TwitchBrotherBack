package com.twitchbrother.back.repository;

import com.twitchbrother.back.model.GamesInformationsList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Basic Redis Repository to store Games Informations
 */
@Repository
public interface GamesInformationsRepository extends CrudRepository<GamesInformationsList, String> {

}
