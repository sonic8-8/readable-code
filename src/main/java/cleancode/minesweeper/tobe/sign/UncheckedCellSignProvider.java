package cleancode.minesweeper.tobe.sign;

import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.CellSnapshotStatus;

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
