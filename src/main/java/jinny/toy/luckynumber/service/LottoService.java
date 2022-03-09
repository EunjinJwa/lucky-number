package jinny.toy.luckynumber.service;

import com.sun.javafx.binding.StringFormatter;
import jinny.toy.luckynumber.data.LottoMemoryData;
import jinny.toy.luckynumber.rest.LotteryRest;
import jinny.toy.luckynumber.struct.dto.LottoNumberDto;
import jinny.toy.luckynumber.struct.model.LottoNumber;
import jinny.toy.luckynumber.struct.model.NumberCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
public class LottoService {

    private LottoMemoryData lottoMemoryData;
    private LotteryRest lotteryRest;
    private GenLottoNumberService genLottoNumberService;

    @Autowired
    public LottoService(LottoMemoryData lottoMemoryData, LotteryRest lotteryRest, GenLottoNumberService genLottoNumberService) {
        this.lottoMemoryData = lottoMemoryData;
        this.lotteryRest = lotteryRest;
        this.genLottoNumberService = genLottoNumberService;
    }

    public LottoNumberDto getLottoNumber(int drwNo) {
        return lotteryRest.getLotteryNumber(drwNo);
    }

    public List<LottoNumberDto> loadLottoNumbers(int from, int to) {
        for (int drwNo = from; drwNo <= to; drwNo++) {
            lottoMemoryData.addLottoNumbers(getLottoNumber(drwNo));
        }
        setNumberCount();
        setNumberCountByLevel();
        return lottoMemoryData.getLottoNumberDtos();
    }

    public void setNumberCountByLevel() {
        Map<NumberCount.CountLevel, List<NumberCount>> numberCountsByLevel = getNumberCountsByLevel();
        lottoMemoryData.setNumberCountsByLevel(numberCountsByLevel);
    }

    private Map<NumberCount.CountLevel, List<NumberCount>> getNumberCountsByLevel() {
        IntSummaryStatistics countSummaryStatistics = getNumberCountStatistics();
        BiFunction<Integer, Long, NumberCount.CountLevel> countLevel = (count, average) ->
                count > (average + 1) ? NumberCount.CountLevel.HIGH : count < (average - 1) ? NumberCount.CountLevel.LOW : NumberCount.CountLevel.MIDDLE;

        Map<NumberCount.CountLevel, List<NumberCount>> numberCountsByLevel = lottoMemoryData.getNumberCounts()
                .stream()
                .collect(Collectors.groupingBy(s -> countLevel.apply(s.getCount(), Math.round(countSummaryStatistics.getAverage()))));

        return numberCountsByLevel;
    }

    private IntSummaryStatistics getNumberCountStatistics() {
        IntSummaryStatistics countSummaryStatistics = lottoMemoryData.getNumberCounts()
                .stream()
                .mapToInt(NumberCount::getCount)
                .summaryStatistics();
        return countSummaryStatistics;
    }

    public void setNumberCount() {
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
    }

    public List<List<Integer>> generateLuckyNumbers(int count) {
        List results = new ArrayList();
        int i = 1;
        while (i <= count) {
            results.add(genLottoNumberService.algorithm1());
            i++;
        }
        return results;
    }

    public String generateLuckyNumbersForString(int count) {
        List<List<Integer>> numbers = generateLuckyNumbers(count);
        StringBuffer sb = new StringBuffer();
        for (List<Integer> n : numbers) {
            sb.append(n + "\n");
        }
        return sb.toString();
    }
}
