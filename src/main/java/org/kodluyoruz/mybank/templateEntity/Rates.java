package org.kodluyoruz.mybank.templateEntity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rates<String, Double> {

    private Map<String,Double> rates;
    private String base;
    private String date;

}