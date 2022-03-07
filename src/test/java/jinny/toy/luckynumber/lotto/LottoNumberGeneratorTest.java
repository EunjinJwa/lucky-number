package jinny.toy.luckynumber.lotto;

import jinny.toy.luckynumber.service.LottoNumberGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class LottoNumberGeneratorTest {

    @Test
    @DisplayName("무작위 랜덤 숫자 6개")
    public void randomNumber() {
        int[] numbers = LottoNumberGenerator.genRandomNumbers();
        Supplier<Boolean> numberRange = () -> IntStream.of(numbers).anyMatch(i -> i < 0 || i > 46);
        Supplier<Long> numberCount = () -> IntStream.of(numbers).distinct().count();

        Assertions.assertThat(numberRange.get()).isFalse();
        Assertions.assertThat(numberCount.get()).isEqualTo(6);
    }

}
