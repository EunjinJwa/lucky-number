package jinny.toy.luckynumber.service;

import jinny.toy.luckynumber.data.LottoMemoryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.IntStream;

@Service
public class LottoNumberGeneratorService {

    private LottoMemoryData lottoMemoryData;

    @Autowired
    public LottoNumberGeneratorService(LottoMemoryData lottoMemoryData) {
        this.lottoMemoryData = lottoMemoryData;
    }

    public static int[] genRandomNumbers() {
        int[] numbers = IntStream.generate(() -> new Random().nextInt(46))
                .limit(6)
                .sorted()
                .toArray();
        return numbers;
    }

}
