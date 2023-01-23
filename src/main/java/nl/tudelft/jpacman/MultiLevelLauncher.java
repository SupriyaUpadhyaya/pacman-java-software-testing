package nl.tudelft.jpacman;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.Level;
/** Used to launch game in 3-level mode
 * Currently functionality is same as single level Launcher
 */
public class MultiLevelLauncher extends Launcher{
    /**
     * To create new game
     */
    private Game game;
    /**
     *
     * @return game
     */
    public Game getGame() {
        return game;
    }
    /**
     * Launch multi level game
     */
    public Game makeGame() {
        GameFactory gf = getGameFactory();
        Level level = makeLevel();
        game = gf.createMultiLevelGame(level, loadPointCalculator());
        return game;
    }
}
