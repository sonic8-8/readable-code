package cleancode.studycafe.tobe.model.pass;

import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudyCafeSeatPassTest {

    // 질문: 모든 public 메서드를 테스트 해야할까요?
    // 만약 단위 테스트를 하지 않아도 되는 메서드가 있다면 어떤 기준으로 분류해야할까요?
    // getter나 단순 출력이라면 검증을 하지 않아도 될 것 같다는 느낌인데,
    // 계산 로직이나 매개변수를 받아서 검증하는 로직이면 검증을 해야할 것 같다는 느낌이 듭니다.
    // 느낌만으로는 애매해서 정확한 기준이 있는지 궁금합니다!

    // 정상적으로 동작하지 않으면 문제가 생길 것 같은 메서드만 단위 테스트를 작성했습니다.

    @DisplayName("좌석권의 정보를 기반으로 할인된 가격을 계산할 수 있다.")
    @Test
    void getDiscountPrice() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 2, 4000, 0.0);

        // 질문: discountRate나 price가 0이하일 경우도 검증을 해야하는지 궁금합니다! CSV 파일을 읽어오는 것이라도 경계값 테스트를 해야할까요?

        // 질문: 매개변수로 null이 들어오는 경우도 테스트해야할까요? CSV 파일을 읽어오는 것이라 null이 들어올 일이 없다고 생각이 드는데, 이런 상황에서도 null 테스트 해야할 지 궁금합니다!

        // when
        int discountPrice = seatPass.getDiscountPrice();

        // then
        assertThat(discountPrice).isZero();
    }

    @DisplayName("좌석권의 타입으로 사물함을 사용할 수 있는지 판별한다.")
    @Test
    void cannotUseLocker() {
        // given
        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 2, 4000, 0.0);
        StudyCafeSeatPass hourlySeatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 2, 4000, 0.0);
        StudyCafeSeatPass weeklySeatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 2, 4000, 0.0);

        // when
        boolean fixedSeatPassCannotUseLocker = fixedSeatPass.cannotUseLocker();
        boolean hourlySeatPassCannotUseLocker = hourlySeatPass.cannotUseLocker();
        boolean weeklySeatPassCannotUseLocker = weeklySeatPass.cannotUseLocker();

        // 질문: boolean 변수명 앞에 명사를 넣으면 읽기 좋은 것 같아서 넣어봤는데, 항상 동사로 시작하는 변수명으로 해야할까요?

        // then
        assertThat(fixedSeatPassCannotUseLocker).isFalse();
        assertThat(hourlySeatPassCannotUseLocker).isTrue();
        assertThat(weeklySeatPassCannotUseLocker).isTrue();

        // 질문: 하나의 테스트로 true와 false를 검증하고 있는데 분리하는 게 좋은지, 합치는 게 좋은지 궁금합니다!
    }

    @DisplayName("좌석권과 매개변수로 받은 사물함권의 타입과 기간이 같다면 true를 반환한다.")
    @Test
    void isSameDurationType_true() {
        // given
        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 250000, 0.1);
        StudyCafeLockerPass sameLockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 10000);

        // when
        boolean isSameDurationType = fixedSeatPass.isSameDurationType(sameLockerPass);

        // then
        assertThat(isSameDurationType).isTrue();
    }

    @DisplayName("좌석권과 매개변수로 받은 사물함권의 타입이나 기간이 하나라도 다르다면 false를 반환한다.")
    @Test
    void isSameDurationType_false() {
        // given
        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 250000, 0.1);
        StudyCafeLockerPass differentDurationLockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12, 10000);
        StudyCafeLockerPass differentTypeLockerPass = StudyCafeLockerPass.of(StudyCafePassType.HOURLY, 12, 10000);
        StudyCafeLockerPass differentLockerPass = StudyCafeLockerPass.of(StudyCafePassType.HOURLY, 12, 10000);


        // when
        boolean isSameDurationType = fixedSeatPass.isSameDurationType(differentDurationLockerPass);
        boolean isSameDurationType2 = fixedSeatPass.isSameDurationType(differentTypeLockerPass);
        boolean isSameDurationType3 = fixedSeatPass.isSameDurationType(differentLockerPass);

        // 질문: 동사로 시작하는 변수명을 지을 경우엔 뒤에 숫자를 붙여서 구분하는게 최선일까요? 실무에선 이런 변수명을 어떻게 처리하는지 궁금합니다!

        // then
        assertThat(isSameDurationType).isFalse();
        assertThat(isSameDurationType2).isFalse();
        assertThat(isSameDurationType3).isFalse();
    }

    @DisplayName("좌석권은 좌석권의 타입을 매개변수로 받아 같은지 비교할 수 있다.")
    @Test
    void isSamePassType() {
        // given
        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 250000, 0.1);
        StudyCafePassType fixedPassType = StudyCafePassType.FIXED;
        StudyCafePassType hourlyPassType = StudyCafePassType.HOURLY;
        StudyCafePassType weeklyPassType = StudyCafePassType.WEEKLY;

        // when
        boolean isSamePassType = fixedSeatPass.isSamePassType(fixedPassType);
        boolean isSamePassType2 = fixedSeatPass.isSamePassType(hourlyPassType);
        boolean isSamePassType3 = fixedSeatPass.isSamePassType(weeklyPassType);

        // then
        assertThat(isSamePassType).isTrue();
        assertThat(isSamePassType2).isFalse();
        assertThat(isSamePassType3).isFalse();
    }

}
