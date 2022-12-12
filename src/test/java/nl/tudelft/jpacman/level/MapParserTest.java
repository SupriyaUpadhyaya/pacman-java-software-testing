package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.npc.ghost.Clyde;
import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class MapParserTest {

    private LevelFactory levelFactory;
    private BoardFactory boardFactory;

    PacManSprites pacManSprites;

    MapParser mapParser;

    @BeforeEach
    void setUp(){
        pacManSprites=new PacManSprites();
        levelFactory= Mockito.mock(LevelFactory.class);
        boardFactory=Mockito.mock(BoardFactory.class);
        mapParser=new MapParser(levelFactory,boardFactory);
        Mockito.when(levelFactory.createGhost()).thenReturn(new Clyde(pacManSprites.getGhostSprite(GhostColor.ORANGE)));
        Mockito.when(levelFactory.createPellet()).thenReturn(new Pellet(10,pacManSprites.getPelletSprite()));
        Mockito.when(boardFactory.createGround()).thenReturn(new BoardFactory(pacManSprites).createGround());
        Mockito.when(boardFactory.createWall()).thenReturn(new BoardFactory(pacManSprites).createWall());

    }

    @Test
    void niceWeather(){
        char [][] grid={{' ',' ','G','.',' ','P','#',' ','.','.',' ','G'},
            {'P',' ','.','.','#','#','.','#',' ',' ','#',' '},
            {'.','#','.',' ',' ','#','.',' ','.','.',' ','G'}};
        mapParser.parseMap(grid);
        Mockito.verify(levelFactory,Mockito.times(3)).createGhost();
        Mockito.verify(levelFactory,Mockito.times(11)).createPellet();
        Mockito.verify(boardFactory,Mockito.times(29)).createGround();
        Mockito.verify(boardFactory,Mockito.times(7)).createWall();
    }
}
