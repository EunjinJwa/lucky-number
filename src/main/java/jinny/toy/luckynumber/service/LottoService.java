package jinny.toy.luckynumber.service;

import jinny.toy.luckynumber.data.LottoMemoryData;
import jinny.toy.luckynumber.rest.LotteryRestTemplate;
import jinny.toy.luckynumber.struct.LottoNumber;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LottoService {

    private LottoMemoryData lottoMemoryData;

    public LottoService(LottoMemoryData lottoMemoryData) {
        this.lottoMemoryData = lottoMemoryData;
    }

    public LottoNumber getLottoNumber(int drwNo) {
        return LotteryRestTemplate.getLotteryNumber(drwNo);
    }

    public List<LottoNumber> loadLottoNumbers(int from, int to) {

        for (int drwNo = from; drwNo <= to; drwNo++) {
            lottoMemoryData.addLottoNumbers(getLottoNumber(drwNo));
        }
        return lottoMemoryData.getLottoNumbers();
    }
}
