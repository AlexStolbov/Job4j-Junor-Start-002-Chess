package ru.astolbov.Models;

import org.junit.Test;
import ru.astolbov.Exceptoins.FigureNotFoundException;
import ru.astolbov.Exceptoins.ImpossibleMoveException;
import ru.astolbov.Exceptoins.OccupiedWayException;
import ru.astolbov.Models.Figures.Bishop;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * Тест шахматной доски.
 */
public class BoardTest {
    /**
     * Создание шахматной доски.
     */
    @Test
    public void whenCreateBoardThenCreateArrayOfCells() {
        Board board = new Board();
        int sizeOfBoard = board.getSizeOfBoard();
        assertThat(sizeOfBoard, is(board.getCells().length));
        assertThat(sizeOfBoard, is(board.getCells()[0].length));
    }

    /**
     * Выполняет ход и возвращает результат.
     * @param board доска
     * @param fromCell откуда ходим
     * @param toCell куда ходим
     * @return результат хода
     */
    private int resultMove(Board board, Cell fromCell, Cell toCell) {
        int result = 0;
        try {
            if (board.move(fromCell, toCell)) {
                result = 1;
            }
        } catch (OccupiedWayException ioe) {
            result = 2;
        } catch (ImpossibleMoveException ime) {
            result = 3;
        } catch (FigureNotFoundException fnfe) {
            result = 4;
        }
        return result;
    }

    /**
     * Проверка корректного хода слона.
     */
    @Test
    public void whenMoveBishopOnValidCellThenReturnTrue() {
        int[][] setCoordinate = new int[6][];
        //структура вложенного массива fromVertical fromHorizontal toVertical toHorizontal
        setCoordinate[0] = new int[]{5, 3, 3, 1};
        setCoordinate[1] = new int[]{1, 1, 7, 7};
        setCoordinate[2] = new int[]{1, 7, 7, 1};
        setCoordinate[3] = new int[]{3, 7, 7, 3};
        setCoordinate[4] = new int[]{1, 7, 6, 2};
        setCoordinate[5] = new int[]{0, 0, 6, 6};

        for (int i = 0; i < setCoordinate.length; i++) {
            int fromVertical    = setCoordinate[i][0];
            int fromHorizontal  = setCoordinate[i][1];
            int toVertical      = setCoordinate[i][2];
            int toHorizontal    = setCoordinate[i][3];
            Board board = new Board();
            Figure bishopB1 = new Bishop(Colors.Black);
            Cell fromCell = board.getCells()[fromVertical][fromHorizontal];
            Cell toCell = board.getCells()[toVertical][toHorizontal];
            fromCell.setFigure(bishopB1);

            int result = resultMove(board, fromCell, toCell);

            //System.out.println(fromVertical + " " + fromHorizontal);

            assertThat(result, is(1));
        }
    }

    /**
     * Проверка хода слона через занятую клетку.
     */
    @Test
    public void whenMoveBishopThroughOccupiedCellThenThrowException() {
        Board board = new Board();
        Figure bishopB1 = new Bishop(Colors.Black);
        Cell fromCell = board.getCells()[1][1];
        fromCell.setFigure(bishopB1);
        Cell toCell = board.getCells()[4][4];
        Figure bishopW1 = new Bishop(Colors.White);
        board.getCells()[3][3].setFigure(bishopW1);

        int result = resultMove(board, fromCell, toCell);

        assertThat(result, is(2));
    }

    /**
     * Проверка получения пути по вертикали доски.
     */
    @Test
    public void whenGetVerticalWayThenReturnCorrectCells() {
        Board board = new Board();

        int[][] setCoordinate = new int[6][];
        //структура вложенного массива fromVertical fromHorizontal toVertical toHorizontal
        setCoordinate[0] = new int[]{5, 1, 5, 4};
        setCoordinate[1] = new int[]{1, 1, 1, 7};
        setCoordinate[2] = new int[]{3, 7, 3, 0};
        setCoordinate[3] = new int[]{1, 7, 1, 4};
        setCoordinate[4] = new int[]{4, 2, 4, 7};
        setCoordinate[5] = new int[]{0, 0, 0, 6};

        for (int i = 0; i < setCoordinate.length; i++) {

            int fromVertical    = setCoordinate[i][0];
            int fromHorizontal  = setCoordinate[i][1];
            int toVertical      = setCoordinate[i][2];
            int toHorizontal    = setCoordinate[i][3];

            assertThat(fromVertical == toVertical, is(true));

            Cell fromCell = board.getCells()[fromVertical][fromHorizontal];
            Cell toCell = board.getCells()[toVertical][toHorizontal];
            Cell[] testWay = board.verticalWayBetweenCells(fromCell, toCell);

            //System.out.println(fromVertical + " " + fromHorizontal);

            assertThat(testWay.length, is(Math.abs(fromHorizontal - toHorizontal)));

            int currHorizontal = fromHorizontal;
            for (int stepWay = 0; stepWay < testWay.length; stepWay++) {
                assertThat(testWay[stepWay].getVertical() == fromVertical, is(true));
                assertThat(Math.abs(testWay[stepWay].getHorizontal() - currHorizontal) == 1, is(true));
                currHorizontal = testWay[stepWay].getHorizontal();
            }
        }
    }

    /**
     * Проверка получения пути по горизонтали доски.
     */
    @Test
    public void whenGetHorizontalWayThenReturnCorrectCells() {
        Board board = new Board();

        int[][] setCoordinate = new int[6][];
        //структура вложенного массива fromVertical fromHorizontal toVertical toHorizontal
        setCoordinate[0] = new int[]{0, 0, 7, 0};
        setCoordinate[1] = new int[]{1, 1, 7, 1};
        setCoordinate[2] = new int[]{1, 7, 4, 7};
        setCoordinate[3] = new int[]{6, 7, 3, 7};
        setCoordinate[4] = new int[]{5, 7, 7, 7};
        setCoordinate[5] = new int[]{7, 0, 0, 0};

        for (int i = 0; i < setCoordinate.length; i++) {

            int fromVertical    = setCoordinate[i][0];
            int fromHorizontal  = setCoordinate[i][1];
            int toVertical      = setCoordinate[i][2];
            int toHorizontal    = setCoordinate[i][3];

            assertThat(fromHorizontal == toHorizontal, is(true));

            Cell fromCell = board.getCells()[fromVertical][fromHorizontal];
            Cell toCell = board.getCells()[toVertical][toHorizontal];
            Cell[] testWay = board.horizontalWayBetweenCells(fromCell, toCell);

            //System.out.println(fromVertical + " " + fromHorizontal);

            assertThat(testWay.length, is(Math.abs(fromVertical - toVertical)));

            int currVertical = fromVertical;
            for (int stepWay = 0; stepWay < testWay.length; stepWay++) {
                assertThat(testWay[stepWay].getHorizontal() == fromHorizontal, is(true));
                assertThat(Math.abs(testWay[stepWay].getVertical() - currVertical) == 1, is(true));
                currVertical = testWay[stepWay].getVertical();
            }
        }
    }

}
