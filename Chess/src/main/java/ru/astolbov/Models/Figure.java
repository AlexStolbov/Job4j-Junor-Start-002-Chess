package ru.astolbov.Models;

import ru.astolbov.Exceptoins.ImpossibleMoveException;

/**
 * Абстрактный класс фигур.
 */
public abstract class Figure {
    /**
     * Цвет фигуры.
     */
    private final Colors color;

    /**
     * Конструктор.
     * @param setColor цвет фигуры
     */
    public Figure(Colors setColor) {
        this.color = setColor;
    }

    /**
     * Проверяет возможность перехода на указанную позицию.
     * @param board - игровое поле
     * @param dist - ячейка, на которую должна пойти фигура
     * @return массив ячеек, которые должна пройти фигура
     * @throws ImpossibleMoveException исключение, если фигура не может пойти на указанную ячейку
     */
    public abstract Cell[] way(Board board, Cell dist) throws ImpossibleMoveException;

}
