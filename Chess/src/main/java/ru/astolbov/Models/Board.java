package ru.astolbov.Models;

import ru.astolbov.Exceptoins.FigureNotFoundException;
import ru.astolbov.Exceptoins.ImpossibleMoveException;
import ru.astolbov.Exceptoins.OccupiedWayException;

/**
 * Шахматная доска.
 */
public class Board {
    /**
     * Размер доски. Доска квадратная.
     */
    private final int sizeOfBoard;
    /**
     * Фигуры, размещенные на доске.
     */
    private Figure[] figures;
    /**
     * Клетки (ячейки) доски.
     */
    private final Cell[][] cells;

    /**
     * Конструктор.
     */
    public Board() {
        this.sizeOfBoard = 8;
        this.cells = createBoardStructure();
    }

    /**
     * Создает структуру ячеек игрового поля.
     * @return массив ячеек
     */
    private Cell[][] createBoardStructure() {
        Cell[][] boardStructure = new Cell[sizeOfBoard][sizeOfBoard];

        for (int horizontal = 0; horizontal < this.sizeOfBoard; horizontal++) {
            for (int vertical = 0; vertical < this.sizeOfBoard; vertical++) {

                //Colors cc = colorOfPoint(horizontal, vertical);
                //Cell nc = new Cell(horizontal, vertical, cc);
                //cells[horizontal][vertical] = nc;

                boardStructure[horizontal][vertical] = new Cell(horizontal, vertical, colorOfPoint(horizontal, vertical));
            }
        }
        return boardStructure;
    }

    /**
     * Возвращает цвет по заданным координатам доски.
     * @param horizontal номер горизонтали
     * @param vertical номер вертикали
     * @return цвет
     */
    private Colors colorOfPoint(int horizontal, int vertical) {
        Colors color;
        if ((horizontal + vertical) % 2 == 0) {
            color = Colors.Black;
        } else {
            color = Colors.White;
        }
        return color;
    }

    /**
     * Размер игрового поля.
     * @return количество ячеек по вертикали или горизонтали
     */
    public int getSizeOfBoard() {
        return sizeOfBoard;
    }

    /**`
     * Возвращает ячейки игрового поля.
     * @return масси ячеек
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Возвращает ячейку, содержащую заданную фигуру.
     * @param figureFind фигура
     * @return ячейка
     */
    public Cell cellContainingFigure(Figure figureFind) {
        Cell findCell = null;
        for (int horizontal = 0; horizontal < sizeOfBoard - 1; horizontal++) {
            for (int vertical = 0; vertical < sizeOfBoard - 1; vertical++) {
                Cell currCell = cells[horizontal][vertical];
                if (currCell.getFigure() == figureFind) {
                    findCell = currCell;
                    break;
                }
                if (findCell != null) {
                    break;
                }
            }
        }
        return findCell;
    }

    /**
     * Возвращает диагональ (набор ячеек), проходящую от первой ко второй ячейке.
     * @param fromCell первая ячейка
     * @param toCell вторая ячейка
     * @return диагональ
     */
    public Cell[] diagonalBetweenCells(Cell fromCell, Cell toCell) {
        int fromHorizontal = fromCell.getHorizontal();
        int fromVertical = fromCell.getVertical();
        int toHorizontal = toCell.getHorizontal();
        int toVertical = toCell.getVertical();

        Cell[] diag = null;
        if (Math.abs(fromHorizontal - toHorizontal) == Math.abs(fromVertical - toVertical)) {

            //заглушка
            diag = new Cell[4];
            diag[0] = cells[1][1];
            diag[1] = cells[2][2];
            diag[2] = cells[3][3];
            diag[3] = cells[4][4];
            //

        }

        return diag;
    }

    /**
     * Возвращает горизонталь (набор ячеек), проходящую от первой ко второй ячейке.
     * @param fromCell первая ячейка
     * @param toCell вторая ячейка
     * @return горизонталь
     */
    public Cell[] horizontalBetweenCells(Cell fromCell, Cell toCell) {
        Cell[] horizontal = null;

        return horizontal;
    }

    /**
     * Возвращает вертикаль (набор ячеек), проходящую от первой ко второй ячейке.
     * @param fromCell первая ячейка
     * @param toCell вторая ячейка
     * @return вертикаль
     */
    public Cell[] verticalBetweenCells(Cell fromCell, Cell toCell) {
        Cell[] horizontal = null;

        return horizontal;
    }

    /**
     * Перемещает фигуру.
     * @param source откуда перемещаем
     * @param dist куда перемещаем
     * @return истина, если пермещение прошло успешно
     * @throws ImpossibleMoveException - ход невозможен
     * @throws OccupiedWayException - путь движения фигуры занят
     * @throws FigureNotFoundException - фигура не найдена в стартовой клетке
     */
    public boolean move(Cell source, Cell dist) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {

        if (source.getFigure() == null) {
            throw new FigureNotFoundException();
        }

        Cell[] way = source.getFigure().way(this, dist);

        for (Cell currCell : way) {
            if (currCell != source) {
                if (currCell.getFigure() != null) {
                    throw new OccupiedWayException();
                }
            }
        }

        return true;
    }
}


