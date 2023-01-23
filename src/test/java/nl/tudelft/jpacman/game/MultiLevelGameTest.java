package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test scenarios for multi level game
 */
public class MultiLevelGameTest {
    /**
     * create game
     */
    private Level level;
    private GameFactory gameFactory;
    private Game game;
    private PlayerFactory playerFactory;
    private PointCalculator pointCalculator;

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
    @Test
    /**
     * isInProgress = false.
     * isAnyPlayerAlive = true.
     * remainingPellets > 0.
     */
    void niceWeatherStart() {
        game = gameFactory.createMultiLevelGame(level, pointCalculator);
        Mockito.when(level.remainingPellets()).thenReturn(1);
        Mockito.when(level.isAnyPlayerAlive()).thenReturn(true);
        assertFalse(game.isInProgress());
        game.start();
        assertTrue(game.isInProgress());
    }

    @Test
    /**
     * isInProgress = false.
     * isAnyPlayerAlive = false.
     */
    void gameNotInProgressPlayerDead() {
        game = gameFactory.createMultiLevelGame(level, pointCalculator);
        Mockito.when(level.remainingPellets()).thenReturn(0);
        Mockito.when(level.isAnyPlayerAlive()).thenReturn(false);
        assertFalse(game.isInProgress());
        game.start();
        assertFalse(game.isInProgress());
    }

    @Test
    /**
     * Only first if block is true.
     */
    void gameInProgressPlayerAlive() {
        game = gameFactory.createMultiLevelGame(level, pointCalculator);
        Mockito.when(level.remainingPellets()).thenReturn(1);
        Mockito.when(level.isAnyPlayerAlive()).thenReturn(true);
        assertFalse(game.isInProgress());
        game.start();
        assertTrue(game.isInProgress());
        game.start();
        assertTrue(game.isInProgress());
    }

}
