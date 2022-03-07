package jinny.toy.luckynumber.data;

import jinny.toy.luckynumber.struct.LottoNumber;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LottoMemoryData {

    private List<LottoNumber> lottoNumbers;

    public void addLottoNumbers(LottoNumber obj) {
        if (lottoNumbers == null)
            lottoNumbers = new ArrayList<>();
        lottoNumbers.add(obj);
    }

    public List<LottoNumber> getLottoNumbers() {
        return lottoNumbers;
    }

}
