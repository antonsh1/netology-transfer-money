package ru.smartjava.objects;

import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

//@ConditionalOnProperty(prefix="transfer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transfer {

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

    @Valid
    Amount amount;

}
