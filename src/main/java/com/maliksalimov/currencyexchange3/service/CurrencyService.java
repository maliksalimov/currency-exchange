package com.maliksalimov.currencyexchange3.service;

import com.maliksalimov.currencyexchange3.dto.CurrencyDto;
import com.maliksalimov.currencyexchange3.dto.CurrencyResponse;
import com.maliksalimov.currencyexchange3.model.ValCurs;
import com.maliksalimov.currencyexchange3.model.ValType;
import com.maliksalimov.currencyexchange3.model.Valute;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
    private final RestTemplate restTemplate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CurrencyResponse getAllCurrencies(String date) {
        String actualDate = date != null ? date : getCurrentDate();
        String url = "https://www.cbar.az/currencies/" + actualDate + ".xml";

        try{
            String xmlResponse = restTemplate.getForObject(url, String.class);
            assert xmlResponse != null;
            ValCurs valCurs = parseXmlResponse(xmlResponse);
            return convertToResponse(valCurs);
        }catch (Exception e){
            throw new RuntimeException("Error getting currencies response");
        }
    }

    public CurrencyDto getCurrencyByCode(String code, String date){
        CurrencyResponse response = getAllCurrencies(date);

        Optional<CurrencyDto> currency = response.getCurrencies().stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst();

        if(currency.isPresent()){
            return currency.get();
        }else{
            throw new RuntimeException("Currency code not found");
        }
    }

    private String getCurrentDate() {
        return LocalDate.now().format(formatter);
    }

    private ValCurs parseXmlResponse(String xmlResponse) throws JAXBException {
        String cleanXml = xmlResponse.replaceAll("<script[^>]*>.*?</script>", "");

        JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(cleanXml);

        return (ValCurs) jaxbUnmarshaller.unmarshal(reader);
    }

    private CurrencyResponse convertToResponse(ValCurs valCurs) {
        List<CurrencyDto> currencies = new ArrayList<>();

        if(valCurs.getValTypes() != null){
            for(ValType valType : valCurs.getValTypes()){
                if(valType.getValutes() != null){
                    for(Valute valute : valType.getValutes()){
                        CurrencyDto currencyDto = new CurrencyDto(
                                valute.getCode(),
                                valute.getName(),
                                valute.getNominal(),
                                valute.getValue(),
                                valType.getType()
                        );
                        currencies.add(currencyDto);
                    }
                }
            }
        }

        return new CurrencyResponse(
                valCurs.getDate(),
                valCurs.getName(),
                valCurs.getDescription(),
                currencies
        );
    }

}
