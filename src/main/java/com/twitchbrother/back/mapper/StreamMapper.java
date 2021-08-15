package com.twitchbrother.back.mapper;

import com.twitchbrother.back.CustomMapperConfiguration;
import com.twitchbrother.back.dto.StreamDto;
import com.twitchbrother.back.model.TwitchStreamsDataModel;
import com.twitchbrother.back.model.TwitchStreamsModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Maps Twitch Streams received datas into convenient StreamDto using NamedQueries
 */
@Mapper(config = CustomMapperConfiguration.class)
public interface StreamMapper {

  @Mapping(target = "totalViewers", source = "data", qualifiedByName = "calculateAllViewersForEachGame")
  @Mapping(target = "gameList", source = "data", qualifiedByName = "gameList")
  StreamDto map(final TwitchStreamsModel twitchStreamsModel);

  @Named("calculateAllViewersForEachGame")
  default int calculateAllViewersForEachGame(ArrayList<TwitchStreamsDataModel> data) {
    return data.stream().map(TwitchStreamsDataModel::getViewer_count)
        .mapToInt(Integer::intValue).sum();
  }

  @Named("gameList")
  default List<String> gameList(ArrayList<TwitchStreamsDataModel> data) {
    return data.stream().map(TwitchStreamsDataModel::getGame_name).distinct()
        .collect(Collectors.toList());
  }
}
