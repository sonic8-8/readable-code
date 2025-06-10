package cleancode.studycafe.tobe.model.pass.locker;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudyCafeLockerPassTest {

    // 질문: 사물함권의 좌석권 타입이라는 용어가 맥락이 없어서 그런지 어색하게 느껴집니다. DisplayName에 맥락을 모두 표현해주는 게 좋을까요?

    // 정상적으로 동작하지 않으면 문제가 생길 것 같은 메서드만 단위 테스트를 작성했습니다.

    @DisplayName("사물함권의 좌석권 타입을 매개변수로 들어온 좌석권 타입과 같은지 비교할 수 있다.")
    @Test
    void isSamePassType() {
        // given
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 10000);

        // when
        boolean isSamePassType = lockerPass.isSamePassType(StudyCafePassType.FIXED);
        boolean isSamePassType2 = lockerPass.isSamePassType(StudyCafePassType.HOURLY);
        boolean isSamePassType3 = lockerPass.isSamePassType(StudyCafePassType.WEEKLY);

        // then
        assertThat(isSamePassType).isTrue();
        assertThat(isSamePassType2).isFalse();
        assertThat(isSamePassType3).isFalse();
    }

    @DisplayName("사물함권의 사용 기간을 매개변수로 들어온 사용 기간과 같은지 비교할 수 있다.")
    @Test
    void isSameDuration() {
        // given
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 10000);

        // when
        boolean isSameDuration = lockerPass.isSameDuration(4);
        boolean isSameDuration2 = lockerPass.isSameDuration(12);
        boolean isSameDuration3 = lockerPass.isSameDuration(-3);

        // then
        assertThat(isSameDuration).isTrue();
        assertThat(isSameDuration2).isFalse();
        assertThat(isSameDuration3).isFalse();
    }

}
