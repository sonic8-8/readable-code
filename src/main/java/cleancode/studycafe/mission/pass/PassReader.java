package cleancode.studycafe.mission.pass;

import cleancode.studycafe.mission.io.StudyCafeFileHandler;
import cleancode.studycafe.mission.pass.lockerpass.LockerPass;
import cleancode.studycafe.mission.pass.seatpass.SeatPass;

import java.util.List;

public class PassReader {
    private final StudyCafeFileHandler studyCafeFileHandler;

    public PassReader() {
        this.studyCafeFileHandler = new StudyCafeFileHandler();
    }

    public List<SeatPass> readStudyCafePasses() {
        return studyCafeFileHandler.readStudyCafePasses();
    }

    public List<LockerPass> readLockerPasses() {
        return studyCafeFileHandler.readLockerPasses();
    }
}
