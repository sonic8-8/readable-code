package cleancode.studycafe.mission;

import cleancode.studycafe.mission.exception.AppException;
import cleancode.studycafe.mission.io.InputHandler;
import cleancode.studycafe.mission.io.OutputHandler;
import cleancode.studycafe.mission.io.StudyCafeFileHandler;
import cleancode.studycafe.mission.pass.StudyCafeLockerPass;
import cleancode.studycafe.mission.pass.StudyCafePass;
import cleancode.studycafe.mission.pass.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final StudyCafeFileHandler studyCafeFileHandler;
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public StudyCafePassMachine() {
        this.studyCafeFileHandler = new StudyCafeFileHandler();
    }

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();
            List<StudyCafePass> studyCafePasses = getStudyCafePasses();
            processOrder(studyCafePassType, studyCafePasses);
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void processOrder(StudyCafePassType studyCafePassType, List<StudyCafePass> studyCafePasses) {
        if (isHourlyPassType(studyCafePassType)) {
            List<StudyCafePass> hourlyPasses = makeHourlyPasses(studyCafePasses);
            outputHandler.showPassListForSelection(hourlyPasses);
            StudyCafePass selectedPass = inputHandler.getSelectPass(hourlyPasses);
            outputHandler.showPassOrderSummary(selectedPass, null);
            return;
        }
        if (isWeeklyPassType(studyCafePassType)) {
            List<StudyCafePass> weeklyPasses = makeWeeklyPasses(studyCafePasses);
            outputHandler.showPassListForSelection(weeklyPasses);
            StudyCafePass selectedPass = inputHandler.getSelectPass(weeklyPasses);
            outputHandler.showPassOrderSummary(selectedPass, null);
            return;
        }
        if (isFixedPassType(studyCafePassType)) {
            List<StudyCafePass> fixedPasses = makeFixedPasses(studyCafePasses);
            outputHandler.showPassListForSelection(fixedPasses);
            StudyCafePass selectedPass = inputHandler.getSelectPass(fixedPasses);

            List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
            StudyCafeLockerPass lockerPass = makeStudyCafeLockerPass(lockerPasses, selectedPass);

            boolean isLockerSelected = false;
            if (isLockerPassEmpty(lockerPass)) {
                outputHandler.askLockerPass(lockerPass);
                isLockerSelected = inputHandler.getLockerSelection();
            }

            if (isLockerSelected) {
                outputHandler.showPassOrderSummary(selectedPass, lockerPass);
            } else {
                outputHandler.showPassOrderSummary(selectedPass, null);
            }
            return;
        }
        throw new AppException("해당하는 이용권 종류가 없습니다. 다시 입력해주세요.");
    }

    private static boolean isLockerPassEmpty(StudyCafeLockerPass lockerPass) {
        return lockerPass != null;
    }

    private StudyCafeLockerPass makeStudyCafeLockerPass(List<StudyCafeLockerPass> lockerPasses, StudyCafePass selectedPass) {
        return lockerPasses.stream()
            .filter(option ->
                option.getPassType() == selectedPass.getPassType()
                    && option.getDuration() == selectedPass.getDuration()
            )
            .findFirst()
            .orElse(null);
    }

    private List<StudyCafePass> getStudyCafePasses() {
        return studyCafeFileHandler.readStudyCafePasses();
    }

    private List<StudyCafePass> makeFixedPasses(List<StudyCafePass> studyCafePasses) {
        return studyCafePasses.stream()
            .filter(studyCafePass -> isFixedPassType(studyCafePass.getPassType()))
            .toList();
    }

    private List<StudyCafePass> makeWeeklyPasses(List<StudyCafePass> studyCafePasses) {
        return studyCafePasses.stream()
            .filter(studyCafePass -> isWeeklyPassType(studyCafePass.getPassType()))
            .toList();
    }

    private List<StudyCafePass> makeHourlyPasses(List<StudyCafePass> studyCafePasses) {
        return studyCafePasses.stream()
            .filter(studyCafePass -> isHourlyPassType(studyCafePass.getPassType()))
            .toList();
    }

    private boolean isFixedPassType(StudyCafePassType studyCafePassType) {
        return studyCafePassType == StudyCafePassType.FIXED;
    }

    private boolean isWeeklyPassType(StudyCafePassType studyCafePassType) {
        return studyCafePassType == StudyCafePassType.WEEKLY;
    }

    private boolean isHourlyPassType(StudyCafePassType studyCafePassType) {
        return studyCafePassType == StudyCafePassType.HOURLY;
    }

}
