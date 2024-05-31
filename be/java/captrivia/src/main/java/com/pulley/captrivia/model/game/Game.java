package com.pulley.captrivia.model.game;

import com.pulley.captrivia.model.gameevent.PlayerScore;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Game {

        private final UUID id;
        private final String name;
        private final int question_count;

        private int player_count = 0;

        private int currentQuestionIndex = 0;

        private String state = "initialize";

        private List<String> players = new ArrayList<String>();

        private Map<String, Integer> playerScores = new HashMap<String, Integer>();

    public Game(String name, int question_count) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.question_count = question_count;
        this.currentQuestionIndex = 0;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuestion_count() {
        return question_count;
    }

    public int getPlayer_count() {
        return player_count;
    }

    public int getCurrentQuestionIndex() { return currentQuestionIndex;}
    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public void setPlayer_count(int player_count) {
        this.player_count = player_count;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        log.info("State on Game "+this.getId()+" just changed to "+state);
        this.state = state;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public void addPlayer(String playerName) {
            for (String player : players) {
                if (player.equals(playerName)) {
                    // don't add same player a second time
                    return;
                }
            }
            players.add(playerName);
            player_count++;
    }

    public void addPointForPlayer(String playerName) {
        Integer previousScore = playerScores.get(playerName);
        previousScore =  (previousScore != null) ? previousScore : 0;
        playerScores.put(playerName, previousScore + 1);
    }

    public List<PlayerScore> getPlayerScores() {
        List<PlayerScore> playerScoreList = new ArrayList<>();
        for (var entry : playerScores.entrySet()) {
            PlayerScore playerScore = new PlayerScore(entry.getKey(), entry.getValue());
            playerScoreList.add(playerScore);
        }
        return playerScoreList;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", question_count=" + question_count +
                ", player_count=" + player_count +
                ", state='" + state + '\'' +
                ", players=" + players +
                '}';
    }
}