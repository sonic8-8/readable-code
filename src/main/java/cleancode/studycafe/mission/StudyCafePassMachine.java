package cleancode.studycafe.mission;

import cleancode.studycafe.mission.exception.AppException;
import cleancode.studycafe.mission.io.InputHandler;
import cleancode.studycafe.mission.io.OutputHandler;
import cleancode.studycafe.mission.pass.*;
import cleancode.studycafe.mission.pass.lockerpass.LockerPass;
import cleancode.studycafe.mission.pass.lockerpass.LockerPassSelection;
import cleancode.studycafe.mission.pass.lockerpass.LockerPasses;
import cleancode.studycafe.mission.pass.seatpass.SeatPass;
import cleancode.studycafe.mission.pass.seatpass.SeatPassType;
import cleancode.studycafe.mission.pass.seatpass.SeatPasses;

import java.util.List;

public class StudyCafePassMachine {

    private final PassReader passReader;
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public StudyCafePassMachine() {
        this.passReader = new PassReader();
    }

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            SeatPassType seatPassType = inputHandler.getPassTypeSelectingUserAction();
            List<SeatPass> seatPasses = passReader.readStudyCafePasses();
            processOrder(seatPassType, seatPasses);
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void processOrder(SeatPassType seatPassType, List<SeatPass> seatPasses) {
        SeatPass selectedPass = getPassFromUser(seatPassType, seatPasses);

        if (isFixedType(selectedPass)) {
            LockerPass lockerPass = findLockerPassBy(selectedPass);
            LockerPassSelection lockerPassSelection = getLockerPassSelectionFromUser(lockerPass);

            if (isLockerPassSelected(lockerPassSelection)) {
                outputHandler.showPassOrderSummary(selectedPass, lockerPass);
                return;
            }
        }
        outputHandler.showPassOrderSummary(selectedPass);
    }

    private boolean isFixedType(SeatPass selectedPass) {
        return selectedPass.getPassType() == SeatPassType.FIXED;
    }

    private SeatPass getPassFromUser(SeatPassType seatPassType, List<SeatPass> seatPasses) {
        SeatPasses passes = SeatPasses.of(seatPasses);
        List<SeatPass> passList = passes.findPassesFrom(seatPassType);
        outputHandler.showPassListForSelection(passList);
        return inputHandler.getSelectPass(passList);
    }

    private LockerPass findLockerPassBy(SeatPass selectedPass) {
        List<LockerPass> lockerPassList = passReader.readLockerPasses();
        LockerPasses lockerPasses = LockerPasses.of(lockerPassList);
        return lockerPasses.findStudyCafeLockerPass(selectedPass);
    }

    private LockerPassSelection getLockerPassSelectionFromUser(LockerPass lockerPass) {
        if (isLockerPassExist(lockerPass)) {
            outputHandler.askLockerPass(lockerPass);
        }
        return inputHandler.getLockerPassSelectionFromUser();
    }

    private boolean isLockerPassExist(LockerPass lockerPass) {
        return lockerPass != null;
    }

    private boolean isLockerPassSelected(LockerPassSelection lockerPassSelection) {
        return lockerPassSelection == LockerPassSelection.SELECTED;
    }
}
