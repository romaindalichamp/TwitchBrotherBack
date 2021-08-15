package com.twitchbrother.back.mapper;

import com.twitchbrother.back.CustomMapperConfiguration;
import com.twitchbrother.back.dto.GamesInformationsListDto;
import com.twitchbrother.back.model.GamesInformationsList;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Map the front-end Game Informations into Redis Hash Games Informations
 */
@Mapper(config = CustomMapperConfiguration.class)
public interface GamesInformationsMapper {

  @Mapping(target = "serialVersionUID", ignore = true)
  GamesInformationsList map(GamesInformationsListDto gamesInformationsListDto);

  @InheritInverseConfiguration
  GamesInformationsListDto mapTo(GamesInformationsList gamesInformationsList);

}
