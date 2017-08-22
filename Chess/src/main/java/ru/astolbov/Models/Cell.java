package ru.astolbov.Models;

/**
 * Ячейка шахматной доски.
 */

public class Cell {
    /**
     * Цвет ячейки.
     */
    private final Colors color;
    /**
     * Горизонталь ячейки.
     */
    private final int horizontal;
    /**
     * Вертикаль ячейки.
     */
    private final int vertical;
    /**
     * Фигура, стоящая на ячейке.
     */
    private Figure figure;

    /**
     * Конструктор.
     * @param setHorizontal горизонталь ячейки
     * @param setVertical вертикаль ячейки
     * @param setColor цвет
     */
    public Cell(int setVertical, int setHorizontal, Colors setColor) {
        this.vertical = setVertical;
        this.horizontal = setHorizontal;
        this.color = setColor;
        this.figure = null;
    }

    /**
     * Возвращает фигуру, стоящуюю на ячейке.
     * @return фигура
     */
    public Figure getFigure() {
        return figure;
    }

    /**
     * Размещает фигуру в ячейку.
     * @param setFigure фигура
     */
    public void setFigure(Figure setFigure) {
        this.figure = setFigure;
    }

    /**
     * Getter.
     * @return horizontal
     */
    public int getHorizontal() {
        return this.horizontal;
    }

    /**
     * Getter.
     * @return vertical
     */
    public int getVertical() {
        return this.vertical;
    }
}
