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
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoNumberServiceTest {

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

    @Test
    void getLowLuckNumbers() {
        int[] lowNumbers = lottoMemoryData.getNumberCounts()
                .stream()
                .sorted(Comparator.comparing(NumberCount::getCount))
                .limit(10)
                .mapToInt(NumberCount::getNumber)
                .toArray();
        System.out.println(Arrays.toString(lowNumbers));
    }

    @Test
    void getCountAverage() {
        IntSummaryStatistics countSummaryStatistics = lottoMemoryData.getNumberCounts()
                .stream()
                .mapToInt(NumberCount::getCount)
                .summaryStatistics();

        System.out.println("총 횟수 : " + countSummaryStatistics.getCount());
        System.out.println("max 당첨 횟수 : " + countSummaryStatistics.getMax());
        System.out.println("min 당첨 횟수 : " + countSummaryStatistics.getMin());
        System.out.println("평균 당첨 횟수 : " + Math.round(countSummaryStatistics.getAverage()));

        BiFunction<Integer, Long, NumberCount.CountLevel> countLevel = (count, average) ->
                count > (average + 1) ? NumberCount.CountLevel.HIGH : count < (average - 1) ? NumberCount.CountLevel.LOW : NumberCount.CountLevel.MIDDLE;


        Map<NumberCount.CountLevel, List<NumberCount>> numberCountsByLevel = lottoMemoryData.getNumberCounts()
                .stream()
                .collect(Collectors.groupingBy(s -> countLevel.apply(s.getCount(), Math.round(countSummaryStatistics.getAverage()))));

        System.out.println(numberCountsByLevel);

    }

}
