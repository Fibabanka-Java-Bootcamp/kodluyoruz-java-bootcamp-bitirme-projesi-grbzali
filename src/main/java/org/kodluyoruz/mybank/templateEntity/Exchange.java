package org.kodluyoruz.mybank.templateEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exchange {
    private Map<String,Double> rates;
    private String base;
    private String date;
}
