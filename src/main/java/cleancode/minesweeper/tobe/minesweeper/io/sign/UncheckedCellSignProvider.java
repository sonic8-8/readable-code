package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;

public class UncheckedCellSignProvider implements CellSignProvidable {

    private static final String UNCHECKED_SIGN = "□";

    @Override
    public String provide(CellSnapshot snapshot) {
        return UNCHECKED_SIGN;
    }

    @Override
    public boolean supports(CellSnapshot snapshot) {
        return snapshot.isSameStatus(CellSnapshotStatus.UNCHECKED);
    }
}
