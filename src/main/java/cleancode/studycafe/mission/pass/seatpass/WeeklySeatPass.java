package cleancode.studycafe.mission.pass.seatpass;

public class WeeklySeatPass implements SeatPass {

    private final SeatPassType passType = SeatPassType.WEEKLY;
    int duration;
    int price;
    double discountRate;

    private WeeklySeatPass(int duration, int price, double discountRate) {
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static WeeklySeatPass of(int duration, int price, double discountRate) {
        return new WeeklySeatPass(duration, price, discountRate);
    }
    @Override
    public SeatPassType getPassType() {
        return passType;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public double getDiscountRate() {
        return discountRate;
    }

    @Override
    public String display() {
        return String.format("%s주권 - %d원", duration, price);
    }
}
