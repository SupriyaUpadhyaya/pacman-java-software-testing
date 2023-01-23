package nl.tudelft.jpacman.integration.suspension;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test class that conducts integration tests for Story 4: Suspend the Game.
 */

public class SuspendGameSystemTest {
    private Launcher launcher;

    /**
     * Start a launcher, which can display the user interface.
     */
    @BeforeEach
    public void before() {

        launcher = new Launcher();
    }

    /**
     * Test that just starts the.
     * game to checks if is indeed in progress.
     */
    @Test
    public void gameSuspendAndRestart() {
        /**
        * Scenario 4.1 : Game is running and user clicks on stop button.
         * Game must stop running.
         * Asset statement in line 48.
        */

        launcher.launch();
        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();
        getGame().stop();

        /**
         * Scenario 4.2 : Game is stopped and user clicks on start button again.
         * Game must start running.
         * */

        assertThat(getGame().isInProgress()).isFalse();
        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();
    }

    /**
     * After test cleanup
     */
    @AfterEach
    public void after() {
        launcher.dispose();
    }

    /**
     * Get game launched.
     * @return
     */
    private Game getGame() {

        return launcher.getGame();
    }
}
