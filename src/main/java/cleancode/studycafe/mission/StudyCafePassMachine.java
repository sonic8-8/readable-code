package cleancode.studycafe.mission;

import cleancode.studycafe.mission.exception.AppException;
import cleancode.studycafe.mission.io.InputHandler;
import cleancode.studycafe.mission.io.OutputHandler;
import cleancode.studycafe.mission.pass.*;
import cleancode.studycafe.mission.pass.lockerpass.LockerPass;
import cleancode.studycafe.mission.pass.lockerpass.LockerPassSelection;
import cleancode.studycafe.mission.pass.seatpass.SeatPass;
import cleancode.studycafe.mission.pass.seatpass.SeatPassType;

import java.util.List;

public class StudyCafePassMachine {

    private final SeatPassProcessor passProcessor;
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public StudyCafePassMachine() {
        this.passProcessor = new SeatPassProcessor();
    }

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            SeatPassType seatPassType = inputHandler.getPassTypeSelectingUserAction();
            List<SeatPass> seatPasses = passProcessor.readStudyCafePasses();
            processOrder(seatPassType, seatPasses);
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void processOrder(SeatPassType seatPassType, List<SeatPass> seatPasses) {
        if (passProcessor.isHourlyPassType(seatPassType)) {
            SeatPass selectedHourlyPass = getHourlyPassFromUser(seatPasses);
            outputHandler.showPassOrderSummary(selectedHourlyPass);
            return;
        }
        if (passProcessor.isWeeklyPassType(seatPassType)) {
            SeatPass selectedWeeklyPass = getWeeklyPassFromUser(seatPasses);
            outputHandler.showPassOrderSummary(selectedWeeklyPass);
            return;
        }
        if (passProcessor.isFixedPassType(seatPassType)) {
            SeatPass selectedFixedPass = getFixedPassFromUser(seatPasses);

            LockerPass lockerPass = findLockerPassBy(selectedFixedPass);

            LockerPassSelection lockerPassSelection = getLockerPassSelectionFromUser(lockerPass);

            if (isLockerPassSelected(lockerPassSelection)) {
                outputHandler.showPassOrderSummary(selectedFixedPass, lockerPass);
                return;
            }
            outputHandler.showPassOrderSummary(selectedFixedPass);
            return;
        }
        
        throw new AppException("해당하는 이용권이 없습니다. 다시 입력해주세요.");
    }

    private SeatPass getHourlyPassFromUser(List<SeatPass> seatPasses) {
        List<SeatPass> hourlyPasses = passProcessor.findHourlyPasses(seatPasses);
        outputHandler.showPassListForSelection(hourlyPasses);
        return inputHandler.getSelectPass(hourlyPasses);
    }

    private SeatPass getWeeklyPassFromUser(List<SeatPass> seatPasses) {
        List<SeatPass> weeklyPasses = passProcessor.findWeeklyPasses(seatPasses);
        outputHandler.showPassListForSelection(weeklyPasses);
        return inputHandler.getSelectPass(weeklyPasses);
    }

    private SeatPass getFixedPassFromUser(List<SeatPass> seatPasses) {
        List<SeatPass> fixedPasses = passProcessor.findFixedPasses(seatPasses);
        outputHandler.showPassListForSelection(fixedPasses);
        return inputHandler.getSelectPass(fixedPasses);
    }

    private LockerPass findLockerPassBy(SeatPass selectedPass) {
        List<LockerPass> lockerPasses = passProcessor.readLockerPasses();
        return passProcessor.findStudyCafeLockerPass(lockerPasses, selectedPass);
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
