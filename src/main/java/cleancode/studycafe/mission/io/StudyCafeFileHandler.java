package cleancode.studycafe.mission.io;

import cleancode.studycafe.mission.pass.lockerpass.LockerPass;
import cleancode.studycafe.mission.pass.seatpass.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StudyCafeFileHandler {

    public List<SeatPass> readStudyCafePasses() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/pass-list.csv"));
            List<SeatPass> seatPasses = new ArrayList<>();
            for (String line : lines) {
                String[] values = line.split(",");
                SeatPassType passType = SeatPassType.valueOf(values[0]);
                int duration = Integer.parseInt(values[1]);
                int price = Integer.parseInt(values[2]);
                double discountRate = Double.parseDouble(values[3]);

                if (isFixed(passType)) {
                    FixedSeatPass fixedSeatPass = FixedSeatPass.of(duration, price, discountRate);
                    seatPasses.add(fixedSeatPass);
                    continue;
                }
                if (isHourly(passType)) {
                    HourlySeatPass hourlySeatPass = HourlySeatPass.of(duration, price, discountRate);
                    seatPasses.add(hourlySeatPass);
                    continue;
                }
                if (isWeekly(passType)) {
                    WeeklySeatPass studyCafeWeeklyPass = WeeklySeatPass.of(duration, price, discountRate);
                    seatPasses.add(studyCafeWeeklyPass);
                }
            }

            return seatPasses;
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

    public List<LockerPass> readLockerPasses() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/locker.csv"));
            List<LockerPass> lockerPasses = new ArrayList<>();
            for (String line : lines) {
                String[] values = line.split(",");
                SeatPassType seatPassType = SeatPassType.valueOf(values[0]);
                int duration = Integer.parseInt(values[1]);
                int price = Integer.parseInt(values[2]);

                LockerPass lockerPass = LockerPass.of(seatPassType, duration, price);
                lockerPasses.add(lockerPass);
            }

            return lockerPasses;
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

    private boolean isWeekly(SeatPassType passType) {
        return passType == SeatPassType.WEEKLY;
    }

    private boolean isHourly(SeatPassType passType) {
        return passType == SeatPassType.HOURLY;
    }

    private boolean isFixed(SeatPassType passType) {
        return passType == SeatPassType.FIXED;
    }

}
