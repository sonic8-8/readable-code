package cleancode.studycafe.tobe.io.provider;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPasses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SeatPassFileReaderTest {

    @DisplayName("CSV 파일을 읽고 StudyCafeSeatPasses를 반환할 수 있다.")
    @Test
    void getSeatPasses() throws IOException {
        // given
        SeatPassFileReader seatPassFileReader = new SeatPassFileReader();
        File tempFile = File.createTempFile("temp", ".csv");
        tempFile.deleteOnExit();

        String contents =
            "HOURLY,2,4000,0.0\n" +
                "HOURLY,4,6500,0.0";
        Files.writeString(tempFile.toPath(), contents);

        String path = tempFile.getPath();

        // when
        StudyCafeSeatPasses seatPasses = seatPassFileReader.getSeatPasses(path);

        // then
        List<StudyCafeSeatPass> seatPassList = seatPasses.findPassBy(StudyCafePassType.HOURLY);
        assertThat(seatPassList).hasSize(2);
        assertThat(seatPassList.get(0).getPassType()).isEqualTo(StudyCafePassType.HOURLY);
    }

    @DisplayName("존재하지 않는 파일 경로를 읽을 경우 RuntimeException이 발생한다.")
    @Test
    void getSeatPasses_exception() {
        // given
        SeatPassFileReader seatPassFileReader = new SeatPassFileReader();

        // when then
        assertThatThrownBy(() -> seatPassFileReader.getSeatPasses(""))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("파일을 읽는데 실패했습니다.");
    }
}
