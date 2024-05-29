package test.java.com.pulley.captrivia.model.playercommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulley.captrivia.model.playercommand.PlayerCommand;
import com.pulley.captrivia.model.playercommand.PlayerCommandCreate;
import org.junit.jupiter.api.Test;

import static io.dropwizard.jackson.Jackson.newObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

public class PlayerCommandTest {
    private static final ObjectMapper MAPPER = newObjectMapper();
    private static final String playerCommandJSON = "{\n" +
            "  \"type\":\"create\",\n" +
            "  \"payload\":{\"name\":\"New Game\",\"question_count\":5},\n" +
            "  \"nonce\":\"72a59c56-c62e-4f11-95bd-d126d0023b1e\"\n" +
            "}";

    @Test
    void seralizesToJSON() throws Exception {
        final PlayerCommandCreate playerCommandCreate = new PlayerCommandCreate("New Game", 5);
        final PlayerCommand playerCommand = new PlayerCommand("72a59c56-c62e-4f11-95bd-d126d0023b1e", playerCommandCreate);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(playerCommandJSON, PlayerCommand.class));

        assertThat(MAPPER.writeValueAsString(playerCommand)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final PlayerCommandCreate playerCommandCreate = new PlayerCommandCreate("New Game", 5);
        final PlayerCommand playerCommand = new PlayerCommand("72a59c56-c62e-4f11-95bd-d126d0023b1e", playerCommandCreate);
        assertThat(MAPPER.readValue(playerCommandJSON, PlayerCommand.class))
                .isEqualTo(playerCommand);
    }

}