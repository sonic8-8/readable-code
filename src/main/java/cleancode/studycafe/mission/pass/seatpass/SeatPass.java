package cleancode.studycafe.mission.pass.seatpass;

public interface SeatPass {
    SeatPassType getPassType();

    int getDuration();

    int getPrice();

    double getDiscountRate();

    String display();
}
