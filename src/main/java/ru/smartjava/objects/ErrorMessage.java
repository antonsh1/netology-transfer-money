package ru.smartjava.objects;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorMessage {

    @NotNull
    private String message;

    @NotNull
    private Integer id;

}
