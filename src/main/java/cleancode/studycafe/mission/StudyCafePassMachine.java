package cleancode.studycafe.mission;

import cleancode.studycafe.mission.exception.AppException;
import cleancode.studycafe.mission.io.InputHandler;
import cleancode.studycafe.mission.io.OutputHandler;
import cleancode.studycafe.mission.pass.*;

import java.util.List;

public class StudyCafePassMachine {

    private final StudyCafePassProcessor passProcessor;
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public StudyCafePassMachine() {
        this.passProcessor = new StudyCafePassProcessor();
    }

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();
            List<StudyCafePass> studyCafePasses = passProcessor.readStudyCafePasses();
            processOrder(studyCafePassType, studyCafePasses);
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void processOrder(StudyCafePassType studyCafePassType, List<StudyCafePass> studyCafePasses) {
        if (passProcessor.isHourlyPassType(studyCafePassType)) {
            StudyCafePass selectedHourlyPass = getHourlyPassFromUser(studyCafePasses);
            outputHandler.showPassOrderSummary(selectedHourlyPass);
            return;
        }
        if (passProcessor.isWeeklyPassType(studyCafePassType)) {
            StudyCafePass selectedWeeklyPass = getWeeklyPassFromUser(studyCafePasses);
            outputHandler.showPassOrderSummary(selectedWeeklyPass);
            return;
        }
        if (passProcessor.isFixedPassType(studyCafePassType)) {
            StudyCafePass selectedFixedPass = getFixedPassFromUser(studyCafePasses);

            StudyCafeLockerPass lockerPass = findLockerPassBy(selectedFixedPass);

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

    private StudyCafePass getHourlyPassFromUser(List<StudyCafePass> studyCafePasses) {
        List<StudyCafePass> hourlyPasses = passProcessor.findHourlyPasses(studyCafePasses);
        outputHandler.showPassListForSelection(hourlyPasses);
        return inputHandler.getSelectPass(hourlyPasses);
    }

    private StudyCafePass getWeeklyPassFromUser(List<StudyCafePass> studyCafePasses) {
        List<StudyCafePass> weeklyPasses = passProcessor.findWeeklyPasses(studyCafePasses);
        outputHandler.showPassListForSelection(weeklyPasses);
        return inputHandler.getSelectPass(weeklyPasses);
    }

    private StudyCafePass getFixedPassFromUser(List<StudyCafePass> studyCafePasses) {
        List<StudyCafePass> fixedPasses = passProcessor.findFixedPasses(studyCafePasses);
        outputHandler.showPassListForSelection(fixedPasses);
        return inputHandler.getSelectPass(fixedPasses);
    }

    private StudyCafeLockerPass findLockerPassBy(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> lockerPasses = passProcessor.readLockerPasses();
        return passProcessor.findStudyCafeLockerPass(lockerPasses, selectedPass);
    }

    private LockerPassSelection getLockerPassSelectionFromUser(StudyCafeLockerPass lockerPass) {
        if (isLockerPassExist(lockerPass)) {
            outputHandler.askLockerPass(lockerPass);
        }
        return inputHandler.getLockerPassSelectionFromUser();
    }

    private boolean isLockerPassExist(StudyCafeLockerPass lockerPass) {
        return lockerPass != null;
    }

    private boolean isLockerPassSelected(LockerPassSelection lockerPassSelection) {
        return lockerPassSelection == LockerPassSelection.SELECTED;
    }
}
