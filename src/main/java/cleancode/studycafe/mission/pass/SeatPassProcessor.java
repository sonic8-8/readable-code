package cleancode.studycafe.mission.pass;

import cleancode.studycafe.mission.io.StudyCafeFileHandler;
import cleancode.studycafe.mission.pass.lockerpass.LockerPass;
import cleancode.studycafe.mission.pass.seatpass.SeatPass;
import cleancode.studycafe.mission.pass.seatpass.SeatPassType;

import java.util.List;

public class SeatPassProcessor {
    private final StudyCafeFileHandler studyCafeFileHandler;

    public SeatPassProcessor() {
        this.studyCafeFileHandler = new StudyCafeFileHandler();
    }

    public List<SeatPass> findFixedPasses(List<SeatPass> seatPasses) {
        return seatPasses.stream()
                .filter(studyCafePass -> isFixedPassType(studyCafePass.getPassType()))
                .toList();
    }

    public List<SeatPass> findWeeklyPasses(List<SeatPass> seatPasses) {
        return seatPasses.stream()
                .filter(studyCafePass -> isWeeklyPassType(studyCafePass.getPassType()))
                .toList();
    }

    public List<SeatPass> findHourlyPasses(List<SeatPass> seatPasses) {
        return seatPasses.stream()
                .filter(studyCafePass -> isHourlyPassType(studyCafePass.getPassType()))
                .toList();
    }

    public boolean isFixedPassType(SeatPassType seatPassType) {
        return seatPassType == SeatPassType.FIXED;
    }

    public boolean isWeeklyPassType(SeatPassType seatPassType) {
        return seatPassType == SeatPassType.WEEKLY;
    }

    public boolean isHourlyPassType(SeatPassType seatPassType) {
        return seatPassType == SeatPassType.HOURLY;
    }

    public List<SeatPass> readStudyCafePasses() {
        return studyCafeFileHandler.readStudyCafePasses();
    }

    public List<LockerPass> readLockerPasses() {
        return studyCafeFileHandler.readLockerPasses();
    }

    public LockerPass findStudyCafeLockerPass(List<LockerPass> lockerPasses, SeatPass selectedPass) {
        return lockerPasses.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                                && option.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);
    }
}
