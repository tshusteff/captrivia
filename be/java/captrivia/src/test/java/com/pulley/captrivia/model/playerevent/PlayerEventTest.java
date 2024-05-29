package test.java.com.pulley.captrivia.model.playerevent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulley.captrivia.model.playerevent.PlayerEvent;
import com.pulley.captrivia.model.playerevent.PlayerEventConnect;
import org.junit.jupiter.api.Test;

import static io.dropwizard.jackson.Jackson.newObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

public class PlayerEventTest {
    private static final ObjectMapper MAPPER = newObjectMapper();
    private static final String playerEventJSON = "{\n" +
            "  \"type\":\"player_connect\",\n" +
            "  \"payload\":{},\n" +
            "  \"player\":\"Joe\"\n" +
            "}";

    @Test
    void seralizesToJSON() throws Exception {
        final PlayerEventConnect playerEventConnect = new PlayerEventConnect();
        final PlayerEvent playerEvent = new PlayerEvent("Joe", playerEventConnect);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(playerEventJSON, PlayerEvent.class));

        assertThat(MAPPER.writeValueAsString(playerEvent)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final PlayerEventConnect playerEventConnect = new PlayerEventConnect();
        final PlayerEvent playerEvent = new PlayerEvent("Joe", playerEventConnect);
        assertThat(MAPPER.readValue(playerEventJSON, PlayerEvent.class))
                .isEqualTo(playerEvent);
    }

}