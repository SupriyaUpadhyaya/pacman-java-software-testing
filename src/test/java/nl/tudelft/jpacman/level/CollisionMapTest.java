package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Test cases for different collision between different units.
 */
abstract class CollisionMapTest {
    /**
     * Create DefaultPointCalculator.
     */
    @Mock
    protected PointCalculator pointCalculator = new DefaultPointCalculator();
    @Mock  protected PacManSprites pacman = new PacManSprites();
    @Mock protected CollisionMap collisionMap;
    @Mock protected Player player;
    int number = 10;
    @Mock
    protected Pellet pellet = new Pellet(number, pacman.getPelletSprite());

    /** When Player colloides with Pellet the player.
     * points increase by the value of the pellet.
     **/
    @Test
    void playerVsPellet() {
        final int number1 = 50;
        final int number2 = 60;
        player.addPoints(number1);
        collisionMap.collide(player, pellet);
        assertThat(player.getScore()).isEqualTo(number2);
        assertThat(player.hasSquare()).isEqualTo(false);
    }
    /** When player colloides ghost the player is not.
     * alive and the killer of player is the ghost that player.
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
     * When Ghost colloides Pallet nothing happens to the player.
     * - Player is alive and score is the same.
     */
    @Test
    void ghostVsPellet() {
        final int number = 50;
        player.addPoints(number);
        Ghost ghost = mock(Ghost.class);
        collisionMap.collide(ghost, pellet);
        assertThat(player.getScore()).isEqualTo(number);
        assertThat(player.isAlive()).isEqualTo(true);
    }

    /**
     * When Ghost colloides with Player the is not alive and.
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
