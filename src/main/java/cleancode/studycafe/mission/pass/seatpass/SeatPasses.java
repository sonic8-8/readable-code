package cleancode.studycafe.mission.pass.seatpass;

import java.util.List;

public class SeatPasses {

    List<SeatPass> seatPasses;

    private SeatPasses(List<SeatPass> seatPasses) {
        this.seatPasses = seatPasses;
    }

    public static SeatPasses of (List<SeatPass> seatPasses) {
        return new SeatPasses(seatPasses);
    }

    public List<SeatPass> findPassesFrom(SeatPassType passType) {
        return seatPasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == passType)
                .toList();
    }
}
