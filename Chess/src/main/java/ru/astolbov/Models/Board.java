package ru.astolbov.Models;

import ru.astolbov.Exceptoins.FigureNotFoundException;
import ru.astolbov.Exceptoins.ImpossibleMoveException;
import ru.astolbov.Exceptoins.OccupiedWayException;

import java.util.Arrays;

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

        for (int vertical = 0; vertical < this.sizeOfBoard; vertical++) {
            for (int horizontal = 0; horizontal < this.sizeOfBoard; horizontal++) {
                boardStructure[vertical][horizontal] = new Cell(vertical, horizontal, colorOfPoint(horizontal, vertical));
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
        return Arrays.stream(cells).flatMap(vert -> Arrays.stream(vert)).filter(el -> el.getFigure() != null && el.getFigure().equals(figureFind)).findAny().get();
    }

    /**
     * Возвращает диагональ (набор ячеек), проходящую от первой ко второй ячейке.
     * @param fromCell первая ячейка
     * @param toCell вторая ячейка
     * @return диагональ
     */
    public Cell[] diagonalBetweenCells(Cell fromCell, Cell toCell) {
        int fromVertical = fromCell.getVertical();
        int fromHorizontal = fromCell.getHorizontal();
        int toVertical = toCell.getVertical();
        int toHorizontal = toCell.getHorizontal();

        Cell[] diag = null;
        if (Math.abs(fromHorizontal - toHorizontal) == Math.abs(fromVertical - toVertical)) {

            diag = new Cell[Math.abs(fromVertical - toVertical)];
            boolean direct = fromVertical < toVertical;
            int index = 0;
            for (int currVertical = wayStart(fromVertical, direct); wayCondition(currVertical, toVertical, direct); currVertical = wayIncrement(currVertical, direct)) {
                int currHorizontal = (toHorizontal - fromHorizontal) * (currVertical - fromVertical) / (toVertical - fromVertical) + fromHorizontal;
                diag[index] = cells[currVertical][currHorizontal];
                index++;
            }
        }

        return diag;
    }

    /**
     * Возвращает начальную вертикаль в диагонали от стартовой вертикали в зависимости от направления.
     * @param fromVert - стартовая вертикаль
     * @param direct - направление
     * @return начальная вертикаль
     */
    private int wayStart(int fromVert, final boolean direct) {
        return direct ? ++fromVert : --fromVert;
    }

    /**
     * Возвращает условие для направления движения по диагонали в зависимости от направления.
     * @param currVert - текущая вертикаль
     * @param toVert - конечная вертикаль
     * @param direct - направление, true - увеличение, false - уменьшение номера вертикали.
     * @return - признак достижения текущей вертикали до конечной
     */
    private boolean wayCondition(final int currVert, final int toVert, final boolean direct) {
        return direct ? currVert <= toVert : currVert >= toVert;
    }

    /**
     *Возвращает следующую вертикаль после текущей в зависимости от направления.
     * @param currVertical - текущая вертикаль
     * @param direct - направление
     * @return - следующая вертикаль
     */
    private int wayIncrement(int currVertical, final boolean direct) {
        return direct ? ++currVertical : --currVertical;
    }

    /**
     * Возвращает путь (набор ячеек) по вертикали, проходящую от первой ко второй ячейке.
     * @param fromCell первая ячейка
     * @param toCell вторая ячейка
     * @return набор ячеек
     */
    public Cell[] verticalWayBetweenCells(Cell fromCell, Cell toCell) {
        int fromVertical = fromCell.getVertical();

        Cell[] horizontal = null;
        if (fromVertical == toCell.getVertical()) {

            int[] line = getLine(fromCell.getHorizontal(), toCell.getHorizontal());
            horizontal = new Cell[line.length];
            for (int i = 0; i < line.length; i++) {
                horizontal[i] = cells[fromVertical][line[i]];
            }
        }
        return horizontal;
    }

    /**
     * Возвращает путь (набор ячеек) по горизонтали, проходящую от первой ко второй ячейке.
     * @param fromCell первая ячейка
     * @param toCell вторая ячейка
     * @return набор ячеек
     */
    public Cell[] horizontalWayBetweenCells(Cell fromCell, Cell toCell) {
        int fromHorizontal = fromCell.getHorizontal();

        Cell[] vertical = null;
        if (fromHorizontal == toCell.getHorizontal()) {

            int[] line = getLine(fromCell.getVertical(), toCell.getVertical());
            vertical = new Cell[line.length];
            for (int i = 0; i < line.length; i++) {
                vertical[i] = cells[line[i]][fromHorizontal];
            }
        }
        return vertical;
    }

    /**
     * Line.
     * @param varFrom start
     * @param varTo finish
     * @return index
     */
    private int[] getLine(int varFrom, int varTo) {
        int[] line = new int[Math.abs(varFrom - varTo)];
        boolean direct = varFrom < varTo;
        int index = 0;
        for (int currLine = wayStart(varFrom, direct); wayCondition(currLine, varTo, direct); currLine = wayIncrement(currLine, direct)) {
            line[index] = currLine;
            index++;
        }
        return line;
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

        Figure movingFigure = source.getFigure();
        Cell[] way = movingFigure.way(this, dist);

        for (Cell currCell : way) {
            if (currCell != source) {
                if (currCell.getFigure() != null) {
                    throw new OccupiedWayException();
                }
            }
        }

        source.setFigure(null);
        dist.setFigure(movingFigure);
        return true;
    }
}


