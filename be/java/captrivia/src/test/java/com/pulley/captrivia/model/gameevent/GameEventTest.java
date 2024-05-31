package test.java.com.pulley.captrivia.model.gameevent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulley.captrivia.model.gameevent.GameEvent;
import com.pulley.captrivia.model.gameevent.GameEventEnd;
import com.pulley.captrivia.model.gameevent.GameEventPlayerEnter;
import com.pulley.captrivia.model.gameevent.PlayerScore;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.dropwizard.jackson.Jackson.newObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

public class GameEventTest {
    private static final ObjectMapper MAPPER = newObjectMapper();
    private final UUID uuid = UUID.fromString("b47ae27f-150b-438f-92b3-d5fd1b07e4de");

    private static final String gameEventEndJSON =
            "{\n" +
                    "  \"type\":\"game_end\",\n" +
                    "  \"id\":\"b47ae27f-150b-438f-92b3-d5fd1b07e4de\",\n" +
                    "  \"payload\":{" +
                    " \"scores\":[" +
                        "{\"name\":\"Joe\",\"score\":\"3\"}," +
                        "{\"name\":\"Tamar\",\"score\":\"4\"}" +
                    "] "+
                    "}\n" +
                    "}";

    @Test
    void seralizesGameEventEndToJSON() throws Exception {
        List<PlayerScore> scores = new ArrayList<PlayerScore>();
        scores.add(new PlayerScore("Joe", 3));
        scores.add(new PlayerScore("Tamar", 4));

        final GameEventEnd gameEventEnd = new GameEventEnd(scores);
        final GameEvent gameEvent = new GameEvent(uuid, gameEventEnd);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(gameEventEndJSON, GameEvent.class));

        assertThat(MAPPER.writeValueAsString(gameEvent)).isEqualTo(expected);
    }

    @Test
    public void deserializesGameEventEndFromJSON() throws Exception {
        List<PlayerScore> scores = new ArrayList<PlayerScore>();
        scores.add(new PlayerScore("Joe", 3));
        scores.add(new PlayerScore("Tamar", 4));

        final GameEventEnd gameEventEnd = new GameEventEnd(scores);
        final GameEvent gameEvent = new GameEvent(uuid, gameEventEnd);
        assertThat(MAPPER.readValue(gameEventEndJSON, GameEvent.class))
                .isEqualTo(gameEvent);
    }

    private static final String gameEventPlayerEnterJSON = "{" +
            "\"id\":\"b47ae27f-150b-438f-92b3-d5fd1b07e4de\"," +
            "\"payload\":{\"" +
                "name\":\"New Player\"," +
                "\"players\":[\"Joe\",\"Tamar\"]," +
                "\"players_ready\":{\"Joe\":false,\"Tamar\":true}," +
                "\"question_count\":3}," +
            "\"type\":\"game_player_enter\"}";
    @Test
    void seralizesGameEventPlayerEnterToJSON() throws Exception {
        List<String> players = new ArrayList<String>();
        players.add("Joe");
        players.add("Tamar");
        Map<String, Boolean> playersReady = new HashMap<String, Boolean>();
        playersReady.put("Tamar", true);
        playersReady.put("Joe", false);

        final GameEventPlayerEnter gameEventPlayerEnter = new GameEventPlayerEnter("New Player", players, playersReady, 3);
        final GameEvent gameEvent = new GameEvent(uuid, gameEventPlayerEnter);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(gameEventPlayerEnterJSON, GameEvent.class));

        assertThat(MAPPER.writeValueAsString(gameEvent)).isEqualTo(expected);
    }

    @Test
    public void deserializesGameEventPlayerEnterFromJSON() throws Exception {
        List<String> players = new ArrayList<String>();
        players.add("Joe");
        players.add("Tamar");
        Map<String, Boolean> playersReady = new HashMap<String, Boolean>();
        playersReady.put("Tamar", true);
        playersReady.put("Joe", false);
        final GameEventPlayerEnter gameEventPlayerEnter = new GameEventPlayerEnter("New Player", players, playersReady, 3);
        final GameEvent gameEvent = new GameEvent(uuid, gameEventPlayerEnter);
        assertThat(MAPPER.readValue(gameEventPlayerEnterJSON, GameEvent.class))
                .isEqualTo(gameEvent);
    }

}