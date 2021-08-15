package com.twitchbrother.back.controller;

import com.twitchbrother.back.dto.GamesInformationsListDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/datasnapshot", consumes = MediaType.APPLICATION_JSON_VALUE)
public interface DataSnapShotController {

  /**
   * Saving current Game Informations datas
   * @param gamesInformationsListDto - the informations we want to persist
   * @return all the object persisted in Redis Server
   */
  @PutMapping
  GamesInformationsListDto saveGameInformations(
      @RequestBody GamesInformationsListDto gamesInformationsListDto);
}