package jinny.toy.luckynumber.service;

import jinny.toy.luckynumber.data.LottoMemoryData;
import jinny.toy.luckynumber.rest.LotteryRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LottoServiceTest {

    private LottoMemoryData lottoMemoryData = new LottoMemoryData();
    private LottoService lottoService = new LottoService(
            lottoMemoryData,
            new LotteryRest(new RestTemplate()),
            new GenLottoNumberService(lottoMemoryData)
            );

    @BeforeEach
    public void loadData() {
        lottoService.loadLottoNumbers(980, 1003);
    }


    @Test
    void generateLuckyNumbers() {
        String result = lottoService.generateLuckyNumbersForString(5);
        System.out.println(result);
    }
}