package cleancode.studycafe.mission.pass.lockerpass;

import cleancode.studycafe.mission.pass.seatpass.SeatPass;

import java.util.List;

public class LockerPasses {

    private final List<LockerPass> lockerPasses;

    private LockerPasses(List<LockerPass> lockerPasses) {
        this.lockerPasses = lockerPasses;
    }

    public static LockerPasses of(List<LockerPass> lockerPasses) {
        return new LockerPasses(lockerPasses);
    }

    public LockerPass findStudyCafeLockerPass(SeatPass selectedPass) {
        return lockerPasses.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                                && option.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);
    }
}
