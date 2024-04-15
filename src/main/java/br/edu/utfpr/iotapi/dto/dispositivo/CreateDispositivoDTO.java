package br.edu.utfpr.iotapi.dto.dispositivo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateDispositivoDTO (
        @NotBlank @Size(min = 2) String nome,
        @NotBlank String descricao,
        @NotBlank String local,
        @NotBlank String endereco) {
}
