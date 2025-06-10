package cleancode.studycafe.tobe.model.order;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudyCafePassOrderTest {

    // 계산 로직을 검증할 필요가 있을 것 같아 단위 테스트를 작성했습니다.

    @DisplayName("주문에 포함된 좌석권과 사물함권으로 총 가격을 계산할 수 있다.")
    @Test
    void getTotalPrice() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 250000, 0.1);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 10000);

        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, lockerPass);

        // when
        int totalPrice = passOrder.getTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(250000 - 25000 + 10000);
    }

    @DisplayName("주문에 좌석권만 포함될 경우에도 총 가격을 계산할 수 있다.")
    @Test
    void getTotalPrice_seatPass_only() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 2, 100000, 0.1);
        StudyCafeLockerPass lockerPass = null;

        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, lockerPass);

        // when
        int totalPrice = passOrder.getTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(100000 - 10000);
    }
}
