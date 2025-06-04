package cleancode.studycafe.mission.pass.lockerpass;

import cleancode.studycafe.mission.pass.seatpass.SeatPassType;

public class LockerPass {

    private final SeatPassType passType;
    private final int duration;
    private final int price;

    private LockerPass(SeatPassType passType, int duration, int price) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
    }

    public static LockerPass of(SeatPassType passType, int duration, int price) {
        return new LockerPass(passType, duration, price);
    }

    public SeatPassType getPassType() {
        return passType;
    }

    public int getDuration() {
        return duration;
    }

    public int getPrice() {
        return price;
    }

    public String display() {
        return String.format("%s주권 - %d원", duration, price);
    }

}
