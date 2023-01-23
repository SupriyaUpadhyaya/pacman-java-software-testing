package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.MultiLevelLauncher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test cases for Multi level game that extends the single level game.
 */
public class MultiGameTransitionTest extends SingleGameTransitionTest {
    /**
     * Launcher of type multi level
     */
    private MultiLevelLauncher launcherMulti;
    private Game game;

    private Player pacman;

    /**
     * test must be updated/adjusted to assert that player won only if last level is won.
     */
    @Override
    public void playerWon() {
        launcherMulti = new MultiLevelLauncher();
        game = launcherMulti.makeGame();
        launcherMulti.withMapFile("/PlayerWithLastPelletToRight.txt").launch();
        game = launcherMulti.getGame();
        game.start();
        pacman = game.getPlayers().get(0);

        int score = pacman.getScore();
        Square next = pacman.getSquare().getSquareAt(Direction.EAST);
        assertThat(next.getOccupants().get(0)).isInstanceOf(Pellet.class);
        game.move(pacman, Direction.EAST);
        assertThat(pacman.getScore()).isEqualTo(score + 10);
        assertThat(pacman.isAlive()).isTrue();
        assertThat(game.isInProgress()).isFalse();

    }

    /**
     * A new test playerWonLevel() must be created to assert that if a level is.
     * won and it is not the last level then the game should start at the next level.
     */
    @Test
    public void playerWonLevel() {
        launcherMulti = new MultiLevelLauncher();
        game = launcherMulti.makeGame();
        assertThat(game.isInProgress()).isFalse();
        launcherMulti.withMapFile("/PlayerWithLastPelletToRight.txt").launch();
        game = launcherMulti.getGame();
        assertThat(game.isInProgress()).isFalse();
        game.start();
        pacman = game.getPlayers().get(0);

        int score = pacman.getScore();
        Square next = pacman.getSquare().getSquareAt(Direction.EAST);
        assertThat(next.getOccupants().get(0)).isInstanceOf(Pellet.class);
        game.move(pacman, Direction.EAST);
        assertThat(pacman.getScore()).isEqualTo(score + 10);
        assertThat(pacman.isAlive()).isTrue();
        assertThat(game.isInProgress()).isFalse();

    }
}
