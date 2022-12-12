package nl.tudelft.jpacman.level;

import org.junit.jupiter.api.BeforeEach;

/**
 * Covers the test scenarios where player unit interacts with other units.
 * This test currently covers only collision scenarios
 */
class DefaultPlayerInteractionMapTest extends CollisionMapTest {

    @Override
    @BeforeEach
    void setUp() {
        collisionMap = new DefaultPlayerInteractionMap(pointCalculator);
        PlayerFactory playerFactory = new PlayerFactory(pacman);
        player = playerFactory.createPacMan();
    }
}
