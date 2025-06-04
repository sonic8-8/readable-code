package cleancode.studycafe.mission.pass.seatpass;

public class FixedSeatPass implements SeatPass {

    private static final SeatPassType passType = SeatPassType.FIXED;
    int duration;
    int price;
    double discountRate;

    private FixedSeatPass(int duration, int price, double discountRate) {
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static FixedSeatPass of(int duration, int price, double discountRate) {
        return new FixedSeatPass(duration, price, discountRate);
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
