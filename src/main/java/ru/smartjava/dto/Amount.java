package ru.smartjava.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Amount {

    @NotNull
    @Min(0)
    private Integer value;
    @NotNull
    @Size(min = 3, max = 3)
    private String currency;

}
