package com.maliksalimov.currencyexchange3.model;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ValType {
    @XmlAttribute(name = "Type")
    private String type;

    @XmlElement(name = "Valute")
    private List<Valute> valutes;
}