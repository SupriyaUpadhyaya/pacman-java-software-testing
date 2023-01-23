package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test scenarios for multi level game.
 */
public class MultiLevelGameTest {
    /**
     * create game.
     */
    private Level level;
    private GameFactory gameFactory;
    private Game game;
    private PlayerFactory playerFactory;
    private PointCalculator pointCalculator;

    public static final int VALUE_1 = 1;
    public static final int VALUE_2 = 0;

    /**
     * Pre setup for tests.
     */
    @BeforeEach
    void setUp() {
        level = Mockito.mock(Level.class);
        playerFactory = Mockito.mock(PlayerFactory.class);
        pointCalculator = Mockito.mock(PointCalculator.class);
        gameFactory = new GameFactory(playerFactory);
        Mockito.when(playerFactory.createPacMan()).thenReturn(
            Mockito.mock(Player.class));

        /**
         * checking 100% branch coverage.
         */
    }

    /**
     * isInProgress = false.
     * isAnyPlayerAlive = true.
     * remainingPellets > 0.
     */
    @Test
    void niceWeatherStart() {
        game = gameFactory.createMultiLevelGame(level, pointCalculator);
        Mockito.when(level.remainingPellets()).thenReturn(VALUE_1);
        Mockito.when(level.isAnyPlayerAlive()).thenReturn(true);
        assertFalse(game.isInProgress());
        game.start();
        assertTrue(game.isInProgress());
    }

    /**
     * isInProgress = false.
     * isAnyPlayerAlive = false.
     */
    @Test
    void gameNotInProgressPlayerDead() {
        game = gameFactory.createMultiLevelGame(level, pointCalculator);
        Mockito.when(level.remainingPellets()).thenReturn(VALUE_2);
        Mockito.when(level.isAnyPlayerAlive()).thenReturn(false);
        assertFalse(game.isInProgress());
        game.start();
        assertFalse(game.isInProgress());
    }

    /**
     * Only first if block is true.
     */
    @Test
    void gameInProgressPlayerAlive() {
        game = gameFactory.createMultiLevelGame(level, pointCalculator);
        Mockito.when(level.remainingPellets()).thenReturn(VALUE_1);
        Mockito.when(level.isAnyPlayerAlive()).thenReturn(true);
        assertFalse(game.isInProgress());
        game.start();
        assertTrue(game.isInProgress());
        game.start();
        assertTrue(game.isInProgress());
    }

}
