package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {

    @DisplayName("사용자가 패스 타입에 맞는 선택지를 입력할 경우 해당하는 StudyCafePassType Enum을 반환한다.")
    @Test
    void getPassTypeSelectingUserAction() {
        // given
        InputHandler inputHandler = new InputHandler();
        String userInput = "1";

        // when
        StudyCafePassType passType = inputHandler.getPassTypeSelectingUserAction(userInput);

        // then
        assertThat(passType).isEqualTo(StudyCafePassType.HOURLY);
    }

    @DisplayName("사용자가 잘못된 패스 타입 선택지를 입력할 경우 예외가 발생한다.")
    @Test
    void getPassTypeSelectingUserAction_exception() {
        // given
        InputHandler inputHandler = new InputHandler();
        String userInput = "아무거나";

        // when then
        assertThatThrownBy(() -> inputHandler.getPassTypeSelectingUserAction(userInput))
            .isInstanceOf(AppException.class)
            .hasMessage("잘못된 입력입니다.");
    }

}
