package test.java.com.pulley.captrivia.model.playercommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulley.captrivia.model.playercommand.PlayerCommandCreate;
import org.junit.jupiter.api.Test;

import static io.dropwizard.jackson.Jackson.newObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

public class PlayerCommandCreateTest {
    private static final ObjectMapper MAPPER = newObjectMapper();
    private static final String playerCommandCreateJSON = "{\n" +
            "  \"name\":\"New Game\",\n" +
            "  \"question_count\":5\n" +
            "}\n";

    @Test
    void seralizesToJSON() throws Exception {
        final PlayerCommandCreate playerCommandCreate = new PlayerCommandCreate("New Game", 5);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(playerCommandCreateJSON, PlayerCommandCreate.class));

        assertThat(MAPPER.writeValueAsString(playerCommandCreate)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final PlayerCommandCreate playerCommandCreate = new PlayerCommandCreate("New Game", 5);
        assertThat(MAPPER.readValue(playerCommandCreateJSON, PlayerCommandCreate.class))
                .isEqualTo(playerCommandCreate);
    }

}