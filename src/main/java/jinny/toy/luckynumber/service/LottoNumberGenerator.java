package jinny.toy.luckynumber.service;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.IntStream;

@Component
public class LottoNumberGenerator {

    public static int[] genRandomNumbers() {
        int[] numbers = IntStream.generate(() -> new Random().nextInt(46))
                .limit(6)
                .sorted()
                .toArray();
        return numbers;
    }

}
