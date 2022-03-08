package jinny.toy.luckynumber.struct.model;

import lombok.Data;

@Data
public class NumberCount {

    private int number;
    private int count;

    public NumberCount(int number, int count) {
        this.number = number;
        this.count = count;
    }
}
