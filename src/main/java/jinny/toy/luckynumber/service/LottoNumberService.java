package jinny.toy.luckynumber.service;

import jinny.toy.luckynumber.data.LottoMemoryData;
import jinny.toy.luckynumber.struct.model.NumberCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class LottoNumberService {

    private LottoMemoryData lottoMemoryData;

    @Autowired
    public LottoNumberService(LottoMemoryData lottoMemoryData) {
        this.lottoMemoryData = lottoMemoryData;
    }

    public static int[] genRandomNumbers() {
        int[] numbers = IntStream.generate(() -> new Random().nextInt(46))
                .limit(6)
                .sorted()
                .toArray();
        return numbers;
    }

    public int[] getMaxLuckNumbers(int count) {
        int[] topNumbers = lottoMemoryData.getNumberCounts()
                .stream()
                .sorted(Comparator.comparing(NumberCount::getCount).reversed())
                .limit(count)
                .mapToInt(NumberCount::getNumber)
                .toArray();
        return topNumbers;
    }

    public int[] getLowLuckNumbers(int count) {
        int[] topNumbers = lottoMemoryData.getNumberCounts()
                .stream()
                .sorted(Comparator.comparing(NumberCount::getCount))
                .limit(count)
                .mapToInt(NumberCount::getNumber)
                .toArray();
        return topNumbers;
    }



}
