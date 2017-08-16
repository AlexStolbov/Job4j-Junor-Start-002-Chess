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
     * Проверка корректного хода слона.
     */
    @Test
    public void whenMoveBishopOnValidCellThenReturnTrue() {
        Board board = new Board();
        Figure bishopB1 = new Bishop(Colors.Black);
        Cell fromCell = board.getCells()[1][1];
        Cell toCell = board.getCells()[4][4];
        fromCell.setFigure(bishopB1);

        int result = resultMove(board, fromCell, toCell);

        assertThat(result, is(1));
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

}
