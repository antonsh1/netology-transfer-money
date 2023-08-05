package ru.smartjava.utils;

import org.springframework.context.annotation.Configuration;
import ru.smartjava.dto.Code;

import java.util.Objects;
@Configuration
public class ConfirmCode {

    private final String DEFAULT_CODE = "0000";

    public Boolean isGood(Code code) {
        return Objects.equals(code.getCode(), DEFAULT_CODE);
    }
}
