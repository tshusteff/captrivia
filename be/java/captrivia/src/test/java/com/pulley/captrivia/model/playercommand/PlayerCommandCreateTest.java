package test.java.com.pulley.captrivia.model.playercommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulley.captrivia.model.playercommand.PlayerCommandCreate;
import org.junit.jupiter.api.Test;

import static io.dropwizard.jackson.Jackson.newObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

public class PlayerCommandCreateTest {
    private static final ObjectMapper MAPPER = newObjectMapper();

    @Test
    void seralizesToJSON() throws Exception {
        final PlayerCommandCreate playerCommandCreate = new PlayerCommandCreate("New Game", 5);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(getClass().getResource("../../resources/fixtures/player_command_create.json"), PlayerCommandCreate.class));

        assertThat(MAPPER.writeValueAsString(playerCommandCreate)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final PlayerCommandCreate playerCommandCreate = new PlayerCommandCreate("New Game", 5);
        assertThat(MAPPER.readValue(getClass().getResource("../../resources/fixtures/player_command_create.json"), PlayerCommandCreate.class))
                .isEqualTo(playerCommandCreate);
    }

}