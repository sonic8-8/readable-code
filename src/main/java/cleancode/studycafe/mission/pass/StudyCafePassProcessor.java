package cleancode.studycafe.mission.pass;

import cleancode.studycafe.mission.io.StudyCafeFileHandler;

import java.util.List;

public class StudyCafePassProcessor {
    private final StudyCafeFileHandler studyCafeFileHandler;

    public StudyCafePassProcessor() {
        this.studyCafeFileHandler = new StudyCafeFileHandler();
    }

    public List<StudyCafePass> findFixedPasses(List<StudyCafePass> studyCafePasses) {
        return studyCafePasses.stream()
                .filter(studyCafePass -> isFixedPassType(studyCafePass.getPassType()))
                .toList();
    }

    public List<StudyCafePass> findWeeklyPasses(List<StudyCafePass> studyCafePasses) {
        return studyCafePasses.stream()
                .filter(studyCafePass -> isWeeklyPassType(studyCafePass.getPassType()))
                .toList();
    }

    public List<StudyCafePass> findHourlyPasses(List<StudyCafePass> studyCafePasses) {
        return studyCafePasses.stream()
                .filter(studyCafePass -> isHourlyPassType(studyCafePass.getPassType()))
                .toList();
    }

    public boolean isFixedPassType(StudyCafePassType studyCafePassType) {
        return studyCafePassType == StudyCafePassType.FIXED;
    }

    public boolean isWeeklyPassType(StudyCafePassType studyCafePassType) {
        return studyCafePassType == StudyCafePassType.WEEKLY;
    }

    public boolean isHourlyPassType(StudyCafePassType studyCafePassType) {
        return studyCafePassType == StudyCafePassType.HOURLY;
    }

    public List<StudyCafePass> readStudyCafePasses() {
        return studyCafeFileHandler.readStudyCafePasses();
    }

    public List<StudyCafeLockerPass> readLockerPasses() {
        return studyCafeFileHandler.readLockerPasses();
    }

    public StudyCafeLockerPass findStudyCafeLockerPass(List<StudyCafeLockerPass> lockerPasses, StudyCafePass selectedPass) {
        return lockerPasses.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                                && option.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);
    }
}
