package nl.tudelft.jpacman.npc.ghost;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Unit test class to test the characteristics of Clyde ghost
 * 1. If it is within the 8 units of player then it will move in opposite direction to player
 * 2. If it is outside of 8 units of player then it will move towards the player
 * @author Supriya Upadhyaya
 */

class ClydeTest {

    private Player player;
    private Ghost clyde;
    private GhostMapParser ghostMapParser;
    private PacManSprites pacManSprites;

    /**
     * Before test method to create PacMan and Clyde ghost, To initialise a GhostMapParser
     * with required Level and Board
    **/
    @BeforeEach
    void createTestScenario(){
        pacManSprites = new PacManSprites();
        BoardFactory boardFactory = new BoardFactory(pacManSprites);
        GhostFactory ghostFactory = new GhostFactory(pacManSprites);
        LevelFactory levelFactory = new LevelFactory(pacManSprites, ghostFactory, new DefaultPointCalculator());
        PlayerFactory playerFactory = new PlayerFactory(pacManSprites);
        ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        player = playerFactory.createPacMan();
    }

    /**
     * Clyde must move in the direction of the player i.e., NORTH as the SHYNESS between
     * Clyde and player set is equal to 8
     **/
    @Test
    void withinShyness(){
        char[][] levelMap = {"############".toCharArray(), "#P        C#".toCharArray(), "############".toCharArray()};

        Level level = ghostMapParser.parseMap(levelMap);
        clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        level.registerPlayer(player);
        player.setDirection(Direction.NORTH);
        assertThat(clyde != null);
        Optional<Direction> clyde_direction = clyde.nextAiMove();
        assertThat(clyde_direction).isEqualTo(Optional.of(Direction.NORTH));
    }

    /**
     * Clyde must move in the direction opposite to that of the player i.e., SOUTH
     * as the SHYNESS between Clyde and player set less than 8
     **/
    @Test
    void outsideShyness(){
        char[][] levelMap = {"############".toCharArray(), "# P       C#".toCharArray(), "############".toCharArray()};

        Level level = ghostMapParser.parseMap(levelMap);
        clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        level.registerPlayer(player);
        player.setDirection(Direction.NORTH);
        assertThat(clyde != null);
        Optional<Direction> clyde_direction = clyde.nextAiMove();
        assertThat(clyde_direction).isEqualTo(Optional.of(Direction.SOUTH));
    }

    /**
     * Clyde must not move when there is no player
     **/
    @Test
    void noPlayer(){
        char[][] levelMap = {"############".toCharArray(), "#         C#".toCharArray(), "############".toCharArray()};

        Level level = ghostMapParser.parseMap(levelMap);
        clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        player.setDirection(Direction.NORTH);
        assertThat(clyde != null);
        Optional<Direction> clyde_direction = clyde.nextAiMove();
        assertThat(clyde_direction).isEmpty();
    }

    /**
     * Clyde must not move when the path between Clyde and player is not empty i.e., if there could be
     * another ghost in between
     **/
    @Test
    void pathNotEmpty(){
        char[][] levelMap = {"############".toCharArray(), "#   C#    P#".toCharArray(), "############".toCharArray()};

        Level level = ghostMapParser.parseMap(levelMap);
        clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        level.registerPlayer(player);
        player.setDirection(Direction.NORTH);
        assertThat(clyde != null);
        Optional<Direction> clyde_direction = clyde.nextAiMove();
        assertThat(clyde_direction).isEmpty();
    }



}
