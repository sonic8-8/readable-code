package cleancode.studycafe.tobe.io.provider;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPasses;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPasses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class LockerPassFileReaderTest {

    // 테스트하기 어려운 구조인 것 같아 String path를 분리했습니다.

    @DisplayName("CSV 파일을 읽고 StudyCafeLockerPasses를 반환할 수 있다.")
    @Test
    void getLockerPasses() throws IOException {
        // given
        LockerPassFileReader lockerPassFileReader = new LockerPassFileReader();

        File tempFile = File.createTempFile("temp", ".csv");
        tempFile.deleteOnExit();

        String contents = "FIXED,4,10000\n" +
            "FIXED,12,30000";
        Files.writeString(tempFile.toPath(), contents);

        String path = tempFile.getPath();

        SeatPassFileReader seatPassFileReader = new SeatPassFileReader();
        StudyCafeSeatPasses seatPasses = seatPassFileReader.getSeatPasses();
        List<StudyCafeSeatPass> seatPassList = seatPasses.findPassBy(StudyCafePassType.FIXED);
        StudyCafeSeatPass fixedSeatPass = seatPassList.get(0);

        // when
        StudyCafeLockerPasses lockerPasses = lockerPassFileReader.getLockerPasses(path);

        // then
        StudyCafeLockerPass lockerPass = lockerPasses.findLockerPassBy(fixedSeatPass)
            .orElseThrow(() -> new IllegalArgumentException("고정 좌석권이 아닙니다."));
        assertThat(lockerPass.getPassType()).isEqualTo(StudyCafePassType.FIXED);
        assertThat(lockerPass.getPrice()).isEqualTo(fixedSeatPass.getPrice());
    }

    @DisplayName("존재하지 않는 파일 경로를 읽을 경우 RuntimeException이 발생한다.")
    @Test
    void getLockerPasses_exception() {
        // given
        LockerPassFileReader lockerPassFileReader = new LockerPassFileReader();

        // when then
        assertThatThrownBy(() -> lockerPassFileReader.getLockerPasses(""))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("파일을 읽는데 실패했습니다.");
    }

}
