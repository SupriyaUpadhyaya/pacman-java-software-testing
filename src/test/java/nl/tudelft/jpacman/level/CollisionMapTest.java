package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Test cases for different collision between different units.
 */
abstract class CollisionMapTest {

    @Mock
    private PointCalculator pointCalculator = new DefaultPointCalculator();
    @Mock  private PacManSprites pacman = new PacManSprites();
    @Mock private CollisionMap collisionMap;
    @Mock private Player player;

    /**
     *
     * @return point calculator.
     */
    PointCalculator getPointCalculator() {
        return this.pointCalculator;
    }

    /**
     * @param collisionMap
     * @return collistionMap.
     */
     void setCollisionMap(CollisionMap collisionMap) {
        this.collisionMap = collisionMap;
    }

    /**
     *
     * @return pacman.
     */
    PacManSprites getPacman() {
        return this.pacman;
    }

    /**
     * @param player
     * @return player.
     */
    void setPlayer(Player player) {
        this.player = player;
    }
    public static final int NUMBER_1 = 50;
    public static final int NUMBER_2 = 60;
    public static final int NUMBER_3 = 10;
    @Mock
    private Pellet pellet = new Pellet(NUMBER_3, pacman.getPelletSprite());

    /**
     * test setup.
     */
    @BeforeEach
    abstract void setUp();

    /** When Player colloides with Pellet the player
     * points increase by the value of the pellet.
     **/
    @Test
    void playerVsPellet() {
        player.addPoints(NUMBER_1);
        collisionMap.collide(player, pellet);
        assertThat(player.getScore()).isEqualTo(NUMBER_2);
        assertThat(player.hasSquare()).isEqualTo(false);
    }
    /** When player colloides ghost the player is not
     * alive and the killer of player is the ghost that player
     * collided with.
     */
    @Test
    void playerVsGhost() {
        Ghost ghost = mock(Ghost.class);
        collisionMap.collide(player, ghost);
        assertThat(player.isAlive()).isEqualTo(false);
        assertThat(player.getKiller()).isEqualTo(ghost);
    }

    /**
     * When Ghost colloides Pallet nothing happens to the player
     * - Player is alive and score is the same.
     */
    @Test
    void ghostVsPellet() {
        player.addPoints(NUMBER_1);
        Ghost ghost = mock(Ghost.class);
        collisionMap.collide(ghost, pellet);
        assertThat(player.getScore()).isEqualTo(NUMBER_1);
        assertThat(player.isAlive()).isEqualTo(true);
    }

    /**
     * When Ghost colloides with Player the is not alive and
     * killer is the ghost that collided with the player.
     */
    @Test
    void ghostVsPlayer() {
        Ghost ghost = mock(Ghost.class);
        collisionMap.collide(ghost, player);
        assertThat(player.isAlive()).isEqualTo(false);
        assertThat(player.getKiller()).isEqualTo(ghost);
    }
}
