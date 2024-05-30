package com.pulley.captrivia.model.game;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class Game {

        private final UUID id;
        private final String name;
        private final int question_count;

        private int player_count = 0;

        private String state = "initialize";

        private List<String> players = new ArrayList<String>();

    public Game(String name, int question_count) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.question_count = question_count;
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