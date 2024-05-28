package test.java.com.pulley.captrivia.model.playercommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulley.captrivia.model.playercommand.PlayerCommand;
import com.pulley.captrivia.model.playercommand.PlayerCommandCreate;
import org.junit.jupiter.api.Test;

import static io.dropwizard.jackson.Jackson.newObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

public class PlayerCommandTest {
    private static final ObjectMapper MAPPER = newObjectMapper();

    @Test
    void seralizesToJSON() throws Exception {
        final PlayerCommandCreate playerCommandCreate = new PlayerCommandCreate("New Game", 5);
        final PlayerCommand playerCommand = new PlayerCommand("72a59c56-c62e-4f11-95bd-d126d0023b1e", playerCommandCreate);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(getClass().getResource("../../resources/fixtures/player_command.json"), PlayerCommand.class));

        assertThat(MAPPER.writeValueAsString(playerCommand)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final PlayerCommandCreate playerCommandCreate = new PlayerCommandCreate("New Game", 5);
        final PlayerCommand playerCommand = new PlayerCommand("72a59c56-c62e-4f11-95bd-d126d0023b1e", playerCommandCreate);
        assertThat(MAPPER.readValue(getClass().getResource("../../resources/fixtures/player_command.json"), PlayerCommand.class))
                .isEqualTo(playerCommand);
//        PlayerCommand returnedPlayerCommand = MAPPER.readValue(getClass().getResource("../../resources/fixtures/player_command.json"), PlayerCommand.class);
//        System.out.println("returnedPlayerCommand class is "+returnedPlayerCommand.getClass());
    }

}