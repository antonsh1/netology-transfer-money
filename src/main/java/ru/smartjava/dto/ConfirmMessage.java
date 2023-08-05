package ru.smartjava.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConfirmMessage {

    @NotNull
    Code code;

    @NotNull
    OperationId operationId;

}
