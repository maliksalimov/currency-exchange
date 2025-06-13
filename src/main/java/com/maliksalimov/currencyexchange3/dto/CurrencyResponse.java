package com.maliksalimov.currencyexchange3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyResponse {
    private String date;
    private String name;
    private String description;
    private List<CurrencyDto> currencies;
}
