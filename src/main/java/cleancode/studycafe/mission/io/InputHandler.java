package cleancode.studycafe.mission.io;

import cleancode.studycafe.mission.exception.AppException;
import cleancode.studycafe.mission.pass.LockerPassSelection;
import cleancode.studycafe.mission.pass.StudyCafePass;
import cleancode.studycafe.mission.pass.StudyCafePassType;

import java.util.List;
import java.util.Scanner;

public class InputHandler {

    private static final Scanner SCANNER = new Scanner(System.in);

    public StudyCafePassType getPassTypeSelectingUserAction() {
        String userInput = SCANNER.nextLine();

        if ("1".equals(userInput)) {
            return StudyCafePassType.HOURLY;
        }
        if ("2".equals(userInput)) {
            return StudyCafePassType.WEEKLY;
        }
        if ("3".equals(userInput)) {
            return StudyCafePassType.FIXED;
        }
        throw new AppException("잘못된 입력입니다.");
    }

    public StudyCafePass getSelectPass(List<StudyCafePass> passes) {
        String userInput = SCANNER.nextLine();
        int selectedIndex = Integer.parseInt(userInput) - 1;
        return passes.get(selectedIndex);
    }

    public LockerPassSelection getLockerPassSelectionFromUser() {
        String userInput = SCANNER.nextLine();
        if ("1".equals(userInput)) {
            return LockerPassSelection.SELECTED;
        }
        if ("2".equals(userInput)) {
            return LockerPassSelection.NOT_SELECTED;
        }
        throw new AppException("잘못된 입력입니다.");
    }

}
