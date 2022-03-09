package jinny.toy.luckynumber.service;

import jinny.toy.luckynumber.data.LottoMemoryData;
import jinny.toy.luckynumber.struct.model.NumberCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenLottoNumberService {

    private LottoMemoryData lottoMemoryData;


    @Autowired
    public GenLottoNumberService(LottoMemoryData lottoMemoryData) {
        this.lottoMemoryData = lottoMemoryData;
    }

    public List<Integer> algorithm1() {
        List<Integer> luckyNumbers = new ArrayList(6);

        Map<NumberCount.CountLevel, List<NumberCount>> numberCountsByLevel = lottoMemoryData.getNumberCountsByLevel();
        List<NumberCount> highNumberCounts = numberCountsByLevel.get(NumberCount.CountLevel.HIGH);
        List<NumberCount> middleNumberCounts = numberCountsByLevel.get(NumberCount.CountLevel.MIDDLE);
        List<NumberCount> lowNumberCounts = numberCountsByLevel.get(NumberCount.CountLevel.LOW);
        System.out.println("highNumberCounts = " + highNumberCounts);
        System.out.println("middleNumberCounts = " + middleNumberCounts);
        System.out.println("lowNumberCounts = " + lowNumberCounts);

        if (highNumberCounts != null) {
            luckyNumbers.addAll(extractNumbers(highNumberCounts, Math.min(highNumberCounts.size(), 3)));
        }
        luckyNumbers.addAll(extractNumbers(middleNumberCounts, Math.min(middleNumberCounts.size(), 2)));
        if (lowNumberCounts != null) {
            luckyNumbers.addAll(extractNumbers(lowNumberCounts, 1));
        }

        if (luckyNumbers.size() < 6) {
            luckyNumbers.addAll(extractNumbers(middleNumberCounts, (6 - luckyNumbers.size())));
        }

        return luckyNumbers.stream().sorted().collect(Collectors.toList());
    }

    public List<Integer> extractNumbers(List<NumberCount> list, int count) {
        List<Integer> results = new ArrayList<>();
        int[] randomArr = new Random().ints(0, list.size())
                .distinct()
                .limit(count)
                .toArray();
        for (int i = 0; i < randomArr.length; i++) {
            results.add(list.get(randomArr[i]).getNumber());
        }
        return results;

    }


}
