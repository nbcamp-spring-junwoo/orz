package com.junwoo.ott.domain.payment.dto.body;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record CardCreateDto(
    @NotNull String cardNumber,
    @Pattern(regexp = "^\\d{2}$") String cardExpirationYear,
    @Pattern(regexp = "^(0[1-9]|1[1,2])$") String cardExpirationMonth,
    @JsonFormat(shape = STRING, pattern = "yyMMdd") LocalDate customerIdentityNumber,
    @Pattern(regexp = "^\\d{2}$") String cardPassword,
    String customerName
) {

}
