package com.maliksalimov.currencyexchange3.controller;

import com.maliksalimov.currencyexchange3.dto.CurrencyDto;
import com.maliksalimov.currencyexchange3.dto.CurrencyResponse;
import com.maliksalimov.currencyexchange3.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/{date:.+}")
    public ResponseEntity<CurrencyResponse> getAllCurrencies(@PathVariable String date) {
        try {
            CurrencyResponse currencyResponse = currencyService.getAllCurrencies(date);
            return ResponseEntity.ok(currencyResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                .body(new CurrencyResponse(
                    null,
                    "Error",
                    "Failed to get currencies: " + e.getMessage(),
                    null
                ));
    }
}

    @GetMapping("/{date}/{code}")
    public ResponseEntity<CurrencyDto> getCurrencyByCode(
            @PathVariable String date,
            @PathVariable String code
    ){
        try{
            CurrencyDto currency = currencyService.getCurrencyByCode(code, date);
            return ResponseEntity.ok(currency);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API is working!");
    }
}