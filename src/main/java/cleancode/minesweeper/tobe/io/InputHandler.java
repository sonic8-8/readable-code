package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.position.CellPosition;

import java.util.Scanner;

public interface InputHandler {

    String getUserInput();

    CellPosition getCellPositionFromUser();
}
