package cleancode.minesweeper.tobe.cell;

public abstract class Cell {

    protected static final String FLAG_SIGN = "⚑";
    protected static final String UNCHECKED_SIGN = "□";
    protected boolean isFlagged;
    protected boolean isOpened;

    public abstract String getSign();

    public abstract boolean isLandMine();

    public abstract boolean hasLandMineCount();

    public void flag() {
        isFlagged = true;
    }

    public void open() {
        isOpened = true;
    }

    public boolean isChecked() {
        return isOpened || isFlagged;
    }

    public boolean isOpened() {
        return isOpened;
    }
}
