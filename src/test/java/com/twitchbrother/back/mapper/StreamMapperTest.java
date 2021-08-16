package com.twitchbrother.back.mapper;

import com.twitchbrother.back.model.TwitchStreamsDataModel;
import com.twitchbrother.back.model.TwitchStreamsModel;
import java.util.ArrayList;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class StreamMapperTest {

  @Autowired
  private StreamMapper streamMapper;

  @Configuration
  @ComponentScan(basePackageClasses = {StreamMapper.class})
  public static class configurationClass {
    // Context loading
  }

  @Test
  @DisplayName("calculateAllViewersForEachGame")
  public void calculateAllViewersForEachGame() {
    TwitchStreamsModel twitchStreamModel = new TwitchStreamsModel();
    TwitchStreamsDataModel twitchStreamsDataModel = new TwitchStreamsDataModel();
    twitchStreamsDataModel.setViewer_count(10);
    twitchStreamsDataModel.setGame_id("42");
    twitchStreamsDataModel.setGame_name("Rainbow");
    TwitchStreamsDataModel twitchStreamsDataModel2 = new TwitchStreamsDataModel();
    twitchStreamsDataModel2.setViewer_count(20);
    twitchStreamsDataModel2.setGame_id("1337");
    twitchStreamsDataModel2.setGame_name("Rainbow");

    twitchStreamModel.setData(new ArrayList<>(
        Arrays.asList(
            twitchStreamsDataModel,
            twitchStreamsDataModel2)));

    Assertions.assertThat(
        this.streamMapper.map(twitchStreamModel).getTotalViewers()).isEqualTo(30);

  }

  @Test
  @DisplayName("gameList")
  public void gameList() {
    TwitchStreamsModel twitchStreamModel = new TwitchStreamsModel();
    TwitchStreamsDataModel twitchStreamsDataModel = new TwitchStreamsDataModel();
    twitchStreamsDataModel.setViewer_count(10);
    twitchStreamsDataModel.setGame_id("42");
    twitchStreamsDataModel.setGame_name("Far Cry 5");
    TwitchStreamsDataModel twitchStreamsDataModel2 = new TwitchStreamsDataModel();
    twitchStreamsDataModel2.setViewer_count(20);
    twitchStreamsDataModel2.setGame_id("1337");
    twitchStreamsDataModel2.setGame_name("Tom Clancy's Rainbow");
    TwitchStreamsDataModel twitchStreamsDataModel3 = new TwitchStreamsDataModel();
    twitchStreamsDataModel3.setViewer_count(20);
    twitchStreamsDataModel3.setGame_id("7882");
    twitchStreamsDataModel3.setGame_name("Assassin's Creed");
    TwitchStreamsDataModel twitchStreamsDataModel4 = new TwitchStreamsDataModel();
    twitchStreamsDataModel4.setViewer_count(20);
    twitchStreamsDataModel4.setGame_id("25");
    twitchStreamsDataModel4.setGame_name("Assassin's Creed");
    TwitchStreamsDataModel twitchStreamsDataModel5 = new TwitchStreamsDataModel();
    twitchStreamsDataModel5.setViewer_count(20);
    twitchStreamsDataModel5.setGame_id("1337");
    twitchStreamsDataModel5.setGame_name("Far Cry 5");

    twitchStreamModel.setData(new ArrayList<>(
        Arrays.asList(
            twitchStreamsDataModel,
            twitchStreamsDataModel2,
            twitchStreamsDataModel3,
            twitchStreamsDataModel4,
            twitchStreamsDataModel5)));

    Assertions.assertThat(
        this.streamMapper.map(twitchStreamModel).getGameList().size()).isEqualTo(3);

  }
}
