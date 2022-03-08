package jinny.toy.luckynumber.service;

import jinny.toy.luckynumber.data.LottoMemoryData;
import jinny.toy.luckynumber.rest.LotteryRest;
import jinny.toy.luckynumber.struct.model.LottoNumber;
import jinny.toy.luckynumber.struct.model.NumberCount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LottoNumberGeneratorServiceTest {

    private LottoMemoryData lottoMemoryData;

    @BeforeEach
    public void setLottoData() {
        lottoMemoryData = new LottoMemoryData();
        for (int drwNo = 980; drwNo < 1003; drwNo++) {
            lottoMemoryData.addLottoNumbers(new LotteryRest(new RestTemplate()).getLotteryNumber(drwNo));
        }

        Map<Integer, Integer> numberCount = new HashMap<>();
        List<LottoNumber> numbers = lottoMemoryData.getLottoNumbers();
        for (LottoNumber lottoNumber : numbers) {
            for (int n : lottoNumber.getNumbers()) {
                numberCount.computeIfPresent(n, (k, v) -> numberCount.get(k) + 1);
                numberCount.computeIfAbsent(n, v -> 1);
            }
        }

        List<NumberCount> numberCounts = numberCount.keySet().stream()
                .map(k -> new NumberCount(k, numberCount.get(k)))
                .collect(Collectors.toList());
        lottoMemoryData.setNumberCounts(numberCounts);
        lottoMemoryData.getNumberCounts()
                .forEach(System.out::println);

    }

    @Test
    @DisplayName("무작위 랜덤 숫자 6개")
    public void randomNumber() {
        int[] numbers = LottoNumberGeneratorService.genRandomNumbers();
        Supplier<Boolean> numberRange = () -> IntStream.of(numbers).anyMatch(i -> i < 0 || i > 46);
        Supplier<Long> numberCount = () -> IntStream.of(numbers).distinct().count();

        Assertions.assertThat(numberRange.get()).isFalse();
        Assertions.assertThat(numberCount.get()).isEqualTo(6);
    }

    @Test
    @DisplayName("가장 많이 당첨된 숫자 10개")
    public void getMaxLuckNumbers() {
        int[] topNumbers = lottoMemoryData.getNumberCounts()
                .stream()
                .sorted(Comparator.comparing(NumberCount::getCount).reversed())
                .limit(10)
                .mapToInt(NumberCount::getNumber)
                .toArray();

        System.out.println("topNumberArr = " + Arrays.toString(topNumbers));
    }

}
