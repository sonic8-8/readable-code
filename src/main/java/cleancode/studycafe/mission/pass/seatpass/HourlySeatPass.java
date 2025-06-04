package cleancode.studycafe.mission.pass.seatpass;

public class HourlySeatPass implements SeatPass {

    private static final SeatPassType passType = SeatPassType.HOURLY;
    int duration;
    int price;
    double discountRate;

    private HourlySeatPass(int duration, int price, double discountRate) {
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static HourlySeatPass of(int duration, int price, double discountRate) {
        return new HourlySeatPass(duration, price, discountRate);
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
        return String.format("%s시간권 - %d원", duration, price);
    }
}
