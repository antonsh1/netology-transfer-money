package ru.smartjava.objects;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Code {

    @NotNull
    String code;

}
