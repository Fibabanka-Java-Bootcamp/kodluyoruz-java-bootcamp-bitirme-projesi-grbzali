package org.kodluyoruz.mybank.templateEntity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Rate {

    private Rates rate;

}
