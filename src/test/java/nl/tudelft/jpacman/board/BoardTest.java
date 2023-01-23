package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    /**
     * Create new board.
     */
    private Board newBoard;

    /**
     * create board matrix.
     */
    @BeforeEach
    void setUp() {
        Square[][] matrix = {
            {new BasicSquare(), new BasicSquare(), new BasicSquare(), new BasicSquare()},
            {new BasicSquare(), new BasicSquare(), new BasicSquare(), new BasicSquare()},
            {new BasicSquare(), new BasicSquare(), new BasicSquare(), new BasicSquare()}};
        newBoard = new Board(matrix);
    }

    /**
     * Within border cases.
     * @param x X-coordinate.
     * @param y Y-coordinate.
     */
    @ParameterizedTest(name = "x,y")
    @CsvSource({"0,2", "1,1", "2,3", "1,2"})
    void withinBorder(int x, int y) {
        assertThat(newBoard.withinBorders(x, y));
    }

    /**
     * Outside border cases.
     * @param x X-coordinate.
     * @param y Y-coordinate
     */
    @ParameterizedTest(name = "x,y")
    @CsvSource({"2,5", "-4,3", "7,0", "5,-1"})
    void outsideBorder(int x, int y) {
        assertThat(!newBoard.withinBorders(x, y));
    }
}
