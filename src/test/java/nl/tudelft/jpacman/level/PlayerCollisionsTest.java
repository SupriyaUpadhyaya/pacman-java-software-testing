package nl.tudelft.jpacman.level;

import org.junit.jupiter.api.BeforeEach;

/**
 * Covers the test scenarios where player unit collides with other units.
 */
class PlayerCollisionsTest extends CollisionMapTest {
    /**
     * pre test setup.
     */
    @BeforeEach
    void setUp() {
        setCollisionMap(new PlayerCollisions(getPointCalculator()));
        PlayerFactory playerFactory = new PlayerFactory(getPacman());
        setPlayer(playerFactory.createPacMan());
    }

}
