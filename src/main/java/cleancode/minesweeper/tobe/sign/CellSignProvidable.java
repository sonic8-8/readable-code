package cleancode.minesweeper.tobe.sign;

import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.CellSnapshotStatus;

public interface CellSignProvidable {

    String provide(CellSnapshot snapshot);

    boolean supports(CellSnapshot snapshot);
}
