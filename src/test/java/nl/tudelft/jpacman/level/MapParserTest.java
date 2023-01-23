package nl.tudelft.jpacman.level;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.npc.ghost.Clyde;
import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Test class for MapParser.
 */
public class MapParserTest {
    /**
     * Creating mocks for MapParser class.
     */
    private LevelFactory levelFactory;
    private BoardFactory boardFactory;
    private PacManSprites pacManSprites;
    private MapParser mapParser;
    public static final int NUM = 10;
    public static final int NUM_1 = 3;
    public static final int NUM_2 = 11;
    public static final int NUM_3 = 29;
    public static final int NUM_4 = 7;

    /**
     * used mockito framework and defined behaviour for the mock objects.
     */
    @BeforeEach
    void setUp() {
        pacManSprites = new PacManSprites();
        levelFactory = Mockito.mock(LevelFactory.class);
        boardFactory = Mockito.mock(BoardFactory.class);
        mapParser = new MapParser(levelFactory, boardFactory);
        Mockito.when(levelFactory.createGhost()).thenReturn(new
            Clyde(pacManSprites.getGhostSprite(GhostColor.ORANGE)));
        Mockito.when(levelFactory.createPellet()).thenReturn(new
            Pellet(NUM, pacManSprites.getPelletSprite()));
        Mockito.when(boardFactory.createGround()).thenReturn(new
            BoardFactory(pacManSprites).createGround());
        Mockito.when(boardFactory.createWall()).thenReturn(new
            BoardFactory(pacManSprites).createWall());

    }

    /**
     * nice weather test where board containing expected characters.
     *
     */
    @Test
    void niceWeather() {
        char[][] grid = {{' ', ' ', 'G', '.', ' ', 'P', '#', ' ', '.', '.', ' ', 'G'},
            {'P', ' ', '.', '.', '#', '#', '.', '#', ' ', ' ', '#', ' '},
            {'.', '#', '.', ' ', ' ', '#', '.', ' ', '.', '.', ' ', 'G'}};
        mapParser.parseMap(grid);
        Mockito.verify(levelFactory,
            Mockito.times(NUM_1)).createGhost();
        Mockito.verify(levelFactory,
            Mockito.times(NUM_2)).createPellet();
        Mockito.verify(boardFactory,
            Mockito.times(NUM_3)).createGround();
        Mockito.verify(boardFactory,
            Mockito.times(NUM_4)).createWall();
    }

    /**
     * bad weather test cases.
     */

    /**
     * checking for invalid characters.
     */
    @Test
    void invalidChar() {
        char[][] grid = {{' ', ' ', 'G', '.', ' ', 'P', '#', '$', '.', '.', ' ', 'G'},
            {'P', ' ', '.', '.', '#', '#', '.', '#', ' ', ' ', '#', ' '},
            {'.', '#', '.', ' ', ' ', '#', '.', ' ', '.', '.', ' ', 'G'}};
        assertThatExceptionOfType(PacmanConfigurationException.class)
            .isThrownBy(() -> {
                mapParser.parseMap(grid);
            })
            .withMessage("Invalid character at 0,7: $")
            .withNoCause();
    }

    /**
     * if the input contains zero rows.
     */
    @Test
    void noRow() {
        assertThatExceptionOfType(PacmanConfigurationException.class)
            .isThrownBy(() -> {
                mapParser.parseMap(new ArrayList<>());
            })
            .withMessage("Input text must consist of at least 1 row.")
            .withNoCause();
    }

    /**
     * if the input is empty.
     */
    @Test
    void emptyInput() {
        List<String> input = Lists.newArrayList("");
        assertThatExceptionOfType(PacmanConfigurationException.class)
            .isThrownBy(() -> {
                mapParser.parseMap(input);
            })
            .withMessage("Input text lines cannot be empty.")
            .withNoCause();
    }


    /**
     * if input is null.
     */
    @Test
    void nullInput() {
        List<String> input = null;
        assertThatExceptionOfType(PacmanConfigurationException.class)
            .isThrownBy(() -> {
                mapParser.parseMap(input);
            })
            .withMessage("Input text cannot be null.")
            .withNoCause();
    }


    /**
     * test to check exception for unequal input width.
     */
    @Test
    void unequalWidth() {
        List<String> input = Lists.newArrayList("GP.######", "...##");
        assertThatExceptionOfType(PacmanConfigurationException.class)
            .isThrownBy(() -> {
                mapParser.parseMap(input);
            })
            .withMessage("Input text lines are not of equal width.")
            .withNoCause();
    }


    /**
     * test to check exception when resource file is not available.
     */
    @Test
    void noResFile() {
        String resFile = "dummy.txt";
        assertThatExceptionOfType(PacmanConfigurationException.class)
            .isThrownBy(() -> {
                mapParser.parseMap(resFile);
            })
            .withMessage("Could not get resource for: " + resFile)
            .withNoCause();
    }
}
