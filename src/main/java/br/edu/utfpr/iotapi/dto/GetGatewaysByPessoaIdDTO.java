package br.edu.utfpr.iotapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GetGatewaysByPessoaIdDTO(
    long id,
    String nome,
    String descricao,
    String endereco) {

}
