package nl.tudelft.jpacman.level;

import org.junit.jupiter.api.BeforeEach;

/**
 * Covers the test scenarios where player unit interacts with other units.
 * This test currently covers only collision scenarios
 */
class DefaultPlayerInteractionMapTest extends CollisionMapTest {

    @BeforeEach
    void setUp() {
        setCollisionMap(new DefaultPlayerInteractionMap(getPointCalculator()));
        PlayerFactory playerFactory = new PlayerFactory(getPacman());
        setPlayer(playerFactory.createPacMan());
    }
}
