package ru.smartjava.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

//@ConditionalOnProperty(prefix="transfer")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Transfer {

    Date date = new Date();

    @NotNull
    //@CreditCardNumber
    @Size(min = 16, max = 16)
    String cardFromNumber;

    @NotNull
    @Size(min = 5, max = 5)
    String cardFromValidTill;

    @NotNull
    @Size(min = 3, max = 3)
    String cardFromCVV;

    @NotNull
//    @CreditCardNumber
    @Size(min = 16, max = 16)
    String cardToNumber;

    String result = "";

    Boolean closed = false;

    @Valid
    Amount amount;

}
