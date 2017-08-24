package ru.astolbov.Models.Figures;

import ru.astolbov.Exceptoins.ImpossibleMoveException;
import ru.astolbov.Models.Board;
import ru.astolbov.Models.Cell;
import ru.astolbov.Models.Colors;
import ru.astolbov.Models.Figure;

/**
 * Пешка.
 */
public class Pawns extends Figure {

    /**
     * Constructor.
     * @param setColor цвет ячейки
     */
    public Pawns(Colors setColor) {
        super(setColor);
    }

    @Override
    public Cell[] way(Board board, Cell dist) throws ImpossibleMoveException {
        Cell startCell = board.cellContainingFigure(this);
        Cell[] vertical = null;
        ImpossibleMoveException exception = null;

        if (dist.getHorizontal() - 1 != startCell.getHorizontal()) {
            exception = new ImpossibleMoveException();
        } else {
            vertical = board.verticalWayBetweenCells(startCell, dist);

            if (vertical == null || vertical.length > 1) {
                exception = new ImpossibleMoveException();
            }
        }

        if (exception != null) {
            throw exception;
        } else {
            return vertical;
        }
    }
}
