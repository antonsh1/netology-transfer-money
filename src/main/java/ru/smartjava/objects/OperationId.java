package ru.smartjava.objects;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OperationId {

    @NotNull
    private String operationId;

}


