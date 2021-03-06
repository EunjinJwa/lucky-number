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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class genLottoNumberServiceTest {

    LottoMemoryData lottoMemoryData;
    GenLottoNumberService genLottoNumberService;


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


        genLottoNumberService = new GenLottoNumberService(lottoMemoryData);
    }



    @Test
    public void algorithm1() {
        int count = 1;
        while (count <= 5) {
            List<Integer> numbers = genLottoNumberService.algorithm1();
            System.out.printf("(%d) -> %s%n", count, numbers);
            count++;
        }
    }


    @Test
    @DisplayName("????????? ?????? ?????? 6???")
    public void randomNumber() {
        int[] numbers = genLottoNumberService.genRandomNumbers(6);
        Supplier<Boolean> numberRange = () -> IntStream.of(numbers).anyMatch(i -> i < 0 || i > 46);
        Supplier<Long> numberCount = () -> IntStream.of(numbers).distinct().count();

        Assertions.assertThat(numberRange.get()).isFalse();
        Assertions.assertThat(numberCount.get()).isEqualTo(6);
    }

}