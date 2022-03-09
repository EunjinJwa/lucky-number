package jinny.toy.luckynumber.data;

import jinny.toy.luckynumber.struct.dto.LottoNumberDto;
import jinny.toy.luckynumber.struct.model.LottoNumber;
import jinny.toy.luckynumber.struct.model.NumberCount;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class LottoMemoryData {

    private List<LottoNumberDto> lottoNumberDtos;
    private List<LottoNumber> lottoNumbers;
    private List<NumberCount> numberCounts;
    private Map<NumberCount.CountLevel, List<NumberCount>> numberCountsByLevel;

    public void addLottoNumbers(LottoNumberDto obj) {
        if (lottoNumberDtos == null)
            lottoNumberDtos = new ArrayList<>();
        lottoNumberDtos.add(obj);

        if (lottoNumbers == null)
            lottoNumbers = new ArrayList<>();
        int[] numbers = {obj.getDrwtNo1(), obj.getDrwtNo2(), obj.getDrwtNo3(), obj.getDrwtNo4(), obj.getDrwtNo5(), obj.getDrwtNo6()};
        lottoNumbers.add(new LottoNumber(obj.getDrwNo(), numbers));
    }

    public void setNumberCounts(List<NumberCount> numberCounts) {
        this.numberCounts = numberCounts;
    }

    public List<NumberCount> getNumberCounts() {
        return numberCounts;
    }

    public List<LottoNumberDto> getLottoNumberDtos() {
        return lottoNumberDtos;
    }

    public List<LottoNumber> getLottoNumbers() {
        return lottoNumbers;
    }

    public Map<NumberCount.CountLevel, List<NumberCount>> getNumberCountsByLevel() {
        return numberCountsByLevel;
    }

    public void setNumberCountsByLevel(Map<NumberCount.CountLevel, List<NumberCount>> numberCountsByLevel) {
        this.numberCountsByLevel = numberCountsByLevel;
    }
}
