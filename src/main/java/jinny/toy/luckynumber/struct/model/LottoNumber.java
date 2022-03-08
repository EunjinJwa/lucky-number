package jinny.toy.luckynumber.struct.model;

import lombok.Data;

@Data
public class LottoNumber {

    private int drwNo;
    private int[] numbers;

    public LottoNumber(int drwNo, int[] numbers) {
        this.drwNo = drwNo;
        this.numbers = numbers;
    }

}
