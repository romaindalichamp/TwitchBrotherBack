package com.twitchbrother.back.mapper;

import com.twitchbrother.back.CustomMapperConfiguration;
import com.twitchbrother.back.dto.StreamDataDto;
import com.twitchbrother.back.model.TwitchStreamsDataModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

/**
 * Maps only few requiered Twitch Streams datas in a light weight version
 */
@Mapper(config = CustomMapperConfiguration.class)
public interface StreamDataMapper {

  @Mapping(target = "game_id", source = "game_id")
  @Mapping(target = "game_name", source = "game_name")
  @Mapping(target = "viewer_count", source = "viewer_count")
  @ValueMapping(target = MappingConstants.NULL, source = MappingConstants.ANY_REMAINING)
  StreamDataDto map(final TwitchStreamsDataModel twitchStreamsDataModel);

}
