package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board newBoard;

    @BeforeEach
    void setUp() {
        Square[][] matrix = {{new BasicSquare(), new BasicSquare(), new BasicSquare(), new BasicSquare()},
            {new BasicSquare(), new BasicSquare(), new BasicSquare(), new BasicSquare()},
            {new BasicSquare(), new BasicSquare(), new BasicSquare(), new BasicSquare()}};
        newBoard = new Board(matrix);
    }

    /**
     * Within border cases
     */
    @ParameterizedTest(name = "x,y")
    @CsvSource({"0,2", "1,1", "2,3", "1,2"})
    void WithinBorder(int x, int y) {
        assertThat(newBoard.withinBorders(x, y));
    }

    /**
     * Outside border cases
     */
    @ParameterizedTest(name = "x,y")
    @CsvSource({"2,5", "-4,3", "7,0", "5,-1"})
    void outsideBorder(int x, int y) {
        assertThat(!newBoard.withinBorders(x,y));
    }
}
