package jinny.toy.luckynumber.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jinny.toy.luckynumber.service.LottoService;
import jinny.toy.luckynumber.struct.dto.LottoNumberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/jinny-lotto")
@Tag(name = "Lotto")
public class LottoController {

    private LottoService lottoService;

    @Autowired
    public LottoController(LottoService lottoService) {
        this.lottoService = lottoService;
    }

    @GetMapping(value = "/drw/{drwNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Lotto Number.")
    public LottoNumberDto getLottoNumber(@PathVariable int drwNo) {
        return lottoService.getLottoNumber(drwNo);
    }

    @GetMapping(value = "/load-data", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LottoNumberDto> loadLottoNumbers(@RequestParam int from,
                                                 @RequestParam int to) {
        return lottoService.loadLottoNumbers(from, to);
    }

    @GetMapping(value = "/generate")
    public String generateLuckyNumbers(@RequestParam int count) {
        return lottoService.generateLuckyNumbersForString(count);
    }

}
