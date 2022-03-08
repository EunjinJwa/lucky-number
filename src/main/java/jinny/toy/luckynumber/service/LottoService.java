package jinny.toy.luckynumber.service;

import jinny.toy.luckynumber.data.LottoMemoryData;
import jinny.toy.luckynumber.rest.LotteryRest;
import jinny.toy.luckynumber.struct.dto.LottoNumberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LottoService {

    private LottoMemoryData lottoMemoryData;
    private LotteryRest lotteryRest;

    @Autowired
    public LottoService(LottoMemoryData lottoMemoryData, LotteryRest lotteryRest) {
        this.lottoMemoryData = lottoMemoryData;
        this.lotteryRest = lotteryRest;
    }

    public LottoNumberDto getLottoNumber(int drwNo) {
        return lotteryRest.getLotteryNumber(drwNo);
    }

    public List<LottoNumberDto> loadLottoNumbers(int from, int to) {

        for (int drwNo = from; drwNo <= to; drwNo++) {
            lottoMemoryData.addLottoNumbers(getLottoNumber(drwNo));
        }
        return lottoMemoryData.getLottoNumberDtos();
    }
}
