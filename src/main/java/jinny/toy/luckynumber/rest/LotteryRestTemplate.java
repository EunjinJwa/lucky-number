package jinny.toy.luckynumber.rest;

import com.google.gson.Gson;
import jinny.toy.luckynumber.struct.LottoNumber;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class LotteryRestTemplate {

    private static RestTemplate restTemplate = new RestTemplateBuilder().build();
    private static Gson gson = new Gson();

    public static LottoNumber getLotteryNumber(final int drwNo) {

        final String url = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity request = new HttpEntity(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("drwNo", drwNo);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, String.class);
            LottoNumber lottoNumber = gson.fromJson(responseEntity.getBody(), LottoNumber.class);
            return lottoNumber;
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
