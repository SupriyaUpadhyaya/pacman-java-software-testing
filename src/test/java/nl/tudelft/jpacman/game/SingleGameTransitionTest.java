package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Test class for Single level game.
 */
public class SingleGameTransitionTest {
    /**
     * create game and launchers.
     */
    private Launcher launcher;
    private Game game;

    private Player pacman;
    public static final int NUM = 10;

    /**
     * Start a single level game using Launcher.
     */
    @BeforeEach
    public void before() {
        launcher = new Launcher();
        game = launcher.makeGame();
    }


    /**
     * Assert that game only starts after start button is clicked.
     */
    @Test
    void gameStart() {
        launcher.launch();
        game = launcher.getGame();
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
    }

    /**
     * Assert that game is not running after player won and player is still alive.
     */
    @Test
    void playerWon() {
        launcher.withMapFile("/PlayerWithLastPelletToRight.txt").launch();
        game = launcher.getGame();
        game.start();
        pacman = game.getPlayers().get(0);

        int score = pacman.getScore();
        Square next = pacman.getSquare().getSquareAt(Direction.EAST);
        assertThat(next.getOccupants().get(0)).isInstanceOf(Pellet.class);
        game.move(pacman, Direction.EAST);
        assertThat(pacman.getScore()).isEqualTo(score + NUM);
        assertThat(pacman.isAlive()).isTrue();
        assertThat(game.isInProgress()).isFalse();
    }

    /**
     * Assert that game is not running player lost and player is not alive.
     */
    @Test
    void playerLost() {
        launcher.withMapFile("/PlayerWithGhostToRight.txt").launch();
        game = launcher.getGame();
        game.start();
        pacman = game.getPlayers().get(0);

        assertThat(pacman.isAlive()).isTrue();
        Square next = pacman.getSquare().getSquareAt(Direction.EAST);
        assertThat(next.getOccupants().get(0)).isInstanceOf(Ghost.class);
        game.move(pacman, Direction.EAST);
        assertThat(pacman.isAlive()).isFalse();
        assertThat(game.isInProgress()).isFalse();
    }

    /**
     * Assert that a game is not in progress when user clicks on stop button.
     */
    @Test
    void gameOnhold() {
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        game.stop();
        assertThat(game.isInProgress()).isFalse();
    }

    /**
     * Assert that a game not in progress is running after user clicks on start button.
     */
    @Test
    void gameResume() {
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        game.stop();
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
    }
}
