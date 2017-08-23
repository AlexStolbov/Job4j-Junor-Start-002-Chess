package ru.astolbov.Models.Figures;

import ru.astolbov.Exceptoins.ImpossibleMoveException;
import ru.astolbov.Models.Board;
import ru.astolbov.Models.Cell;
import ru.astolbov.Models.Colors;
import ru.astolbov.Models.Figure;

/**
 * Шахматная фигура. Слон.
 */
public class Bishop extends Figure {

    /**
     * Constructor.
     * @param setColor цвет ячейки
     */
    public Bishop(Colors setColor) {
        super(setColor);
    }

    @Override
    public Cell[] way(Board board, Cell dist) throws ImpossibleMoveException {

        Cell startCell = board.cellContainingFigure(this);
        Cell[] diagonal = board.diagonalBetweenCells(startCell, dist);

        if (diagonal == null) {
            throw new ImpossibleMoveException();
        }
        return diagonal;
    }
}
