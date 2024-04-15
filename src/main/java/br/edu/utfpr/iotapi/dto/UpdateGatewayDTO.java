package br.edu.utfpr.iotapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateGatewayDTO(
    @NotBlank @Size(min = 2) String nome,
    @NotBlank String descricao,
    @NotBlank String endereco,
    long pessoaId) {
}
