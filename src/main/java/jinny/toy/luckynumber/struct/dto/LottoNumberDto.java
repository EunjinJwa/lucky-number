package jinny.toy.luckynumber.struct.dto;

import lombok.Data;

@Data
public class LottoNumberDto {

    private long totSellamnt;
    private String returnValue;
    private String drwNoDate;
    private int drwNo;          // 회차
    private long firstWinamnt;
    private int drwtNo1;
    private int drwtNo2;
    private int drwtNo3;
    private int drwtNo4;
    private int drwtNo5;
    private int drwtNo6;
    private int bnusNo;
    private int firstPrzwnerCo;
    private long firstAccumamnt;
}
