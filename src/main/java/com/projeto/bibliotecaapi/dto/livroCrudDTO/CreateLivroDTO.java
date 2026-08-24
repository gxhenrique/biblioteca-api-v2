package com.projeto.bibliotecaapi.dto.livroCrudDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateLivroDTO(
        @NotBlank
        String titulo,
        @NotNull
        Long autorId,
        @NotNull
        Long categoriaId,
        @Positive
        Integer paginas,
        @PositiveOrZero
        BigDecimal preco
) {
}
