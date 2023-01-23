package nl.tudelft.jpacman.integration.suspension;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test class that conducts integration tests for Story 2: Move the player.
 */

public class MovePlayerSystemTest {

    private Launcher launcher = new Launcher();
    private Game game;
    private Player pacman;
    public static final int  POINTS = 10;

    /**
     * Assert that when player moved to a cell with a pallet.
     * the score must increase by 10 points.
     */

    @Test
    public void playerMovesToPelletCell() {
        launcher.withMapFile("/PlayerWithPalletToRight.txt").launch();
        game = launcher.getGame();
        game.start();
        pacman = game.getPlayers().get(0);
        int score = pacman.getScore();
        Square next = pacman.getSquare().getSquareAt(Direction.EAST);
        assertThat(next.getOccupants().get(0)).isInstanceOf(Pellet.class);
        game.move(pacman, Direction.EAST);
        assertThat(pacman.getScore()).isEqualTo(score + POINTS);
    }

    /**
     * Assert that when a player moved to an empty.
     * there is no change in score.
     */

    @Test
    public void playerMovesToEmptyCell() {
        launcher.withMapFile("/PlayerWithEmptyCellToRight.txt").launch();
        game = launcher.getGame();
        game.start();
        pacman = game.getPlayers().get(0);

        int score = pacman.getScore();
        Square next = pacman.getSquare().getSquareAt(Direction.EAST);
        assertThat(next.getOccupants().size()).isEqualTo(0);
        game.move(pacman, Direction.EAST);
        assertThat(pacman.getScore()).isEqualTo(score);

    }

    /**
     * Assert that a player cannot move to a.
     * cell with well.
     */

    @Test
    public void playerMovesTowardsWall() {
        launcher.withMapFile("/PlayerWithWallToRight.txt").launch();
        game = launcher.getGame();
        game.start();
        pacman = game.getPlayers().get(0);

        int score = pacman.getScore();
        Square next = pacman.getSquare().getSquareAt(Direction.EAST);
        assertThat(pacman.getSquare().getSquareAt(Direction.EAST).isAccessibleTo(pacman)).isFalse();
        game.move(pacman, Direction.EAST);
        assertThat(pacman.getScore()).isEqualTo(score);
        assertThat(pacman.getSquare().getSquareAt(Direction.EAST).isAccessibleTo(pacman)).isFalse();

    }

    /**
     * Assert that when a player moved to a cell with ghost.
     * player is no longer alive and game is not running.
     */
    @Test
    public void playerMovesTowardsGhost() {
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
     * Assert that when player moves towards the last pellet.
     * score increases by 10.
     * player is still alive before game is no longer in progress.
     */
    @Test
    public void playerMovesTowardsLastPellet() {
        launcher.withMapFile("/PlayerWithLastPelletToRight.txt").launch();
        game = launcher.getGame();
        game.start();
        pacman = game.getPlayers().get(0);
        int score = pacman.getScore();
        Square next = pacman.getSquare().getSquareAt(Direction.EAST);
        assertThat(next.getOccupants().get(0)).isInstanceOf(Pellet.class);
        game.move(pacman, Direction.EAST);
        assertThat(pacman.getScore()).isEqualTo(score + POINTS);
        assertThat(pacman.isAlive()).isTrue();
        assertThat(game.isInProgress()).isFalse();
    }
}
