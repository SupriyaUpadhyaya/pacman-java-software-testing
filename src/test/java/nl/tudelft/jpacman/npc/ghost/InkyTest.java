package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InkyTest {
    /**
     * create player, ghost and pac-man.
     **/
    private Player player;
    private Ghost inky;
    private Ghost blinky;
    private GhostMapParser ghostMapParser;
    private PacManSprites pacManSprites;

    /**
     * Before test method to create PacMan and inky ghost, To initialise a GhostMapParser.
     * with required Level and Board.
     **/

    @BeforeEach
    void setUp() {
        pacManSprites = new PacManSprites();
        BoardFactory boardFactory = new BoardFactory(pacManSprites);
        GhostFactory ghostFactory = new GhostFactory(pacManSprites);
        LevelFactory levelFactory = new LevelFactory(pacManSprites, ghostFactory, new DefaultPointCalculator());
        PlayerFactory playerFactory = new PlayerFactory(pacManSprites);
        ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        player = playerFactory.createPacMan();
    }

    /**
     * When there is a wall in between Inky and Blinky --> Inky must not move.
     **/
    @Test
    void wallInBetween() {
        char[][] levelMap = {"############".toCharArray(),
            "#I #B P     #".toCharArray(),
            "############".toCharArray()};

        Level level = ghostMapParser.parseMap(levelMap);
        inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        level.registerPlayer(player);
        player.setDirection(Direction.NORTH);
        assertThat(inky != null);
        Optional<Direction> inkyDirection = inky.nextAiMove();
        assertThat(inkyDirection).isEmpty();
    }

    /**
     * When Inky is with Blinky and they are behind player --> Inky is expected to follow Blinky.
     **/
    @Test
    void togetherBehindPlayer() {
        char[][] levelMap = {"############".toCharArray(),
            "#IB P      #".toCharArray(),
            "############".toCharArray()};

        Level level = ghostMapParser.parseMap(levelMap);
        inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        level.registerPlayer(player);
        player.setDirection(Direction.NORTH);
        assertThat(inky != null);
        Optional<Direction> inkyDirection = inky.nextAiMove();
        Optional<Direction> blinkyDirection = blinky.nextAiMove();
        assertThat(inkyDirection).isEqualTo(blinkyDirection);
    }

    /**
     * When there is no blinky on board --> Inky does not move.
     **/
    @Test
    void noBlinky() {
        char[][] levelMap = {"############".toCharArray(),
            "#I   P     #".toCharArray(),
            "############".toCharArray()};

        Level level = ghostMapParser.parseMap(levelMap);
        inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        level.registerPlayer(player);
        player.setDirection(Direction.NORTH);
        assertThat(inky != null);
        Optional<Direction> inkyDirection = inky.nextAiMove();
        assertThat(inkyDirection).isEmpty();
    }

    /**
     * When there is no player on board --> Inky must not move.
     * with required Level and Board.
     **/
    @Test
    void noPlayer() {
        char[][] levelMap = {"############".toCharArray(),
            "#IB        #".toCharArray(),
            "############".toCharArray()};

        Level level = ghostMapParser.parseMap(levelMap);
        inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky != null);
        Optional<Direction> inkyDirection = inky.nextAiMove();
        assertThat(inkyDirection).isEmpty();
    }

    /**
     * When player in between inky and blinky --> inky moves away from player.
     * with required Level and Board.
     **/
    @Test
    void playerInBetween() {
        char[][] levelMap = {"############".toCharArray(),
            "#I  P B    #".toCharArray(),
            "############".toCharArray()};

        Level level = ghostMapParser.parseMap(levelMap);
        inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        level.registerPlayer(player);
        player.setDirection(Direction.NORTH);
        assertThat(inky != null);
        Optional<Direction> inkyDirection = inky.nextAiMove();
        assertThat(inkyDirection).isEqualTo(Optional.of(Direction.SOUTH));
    }


}
