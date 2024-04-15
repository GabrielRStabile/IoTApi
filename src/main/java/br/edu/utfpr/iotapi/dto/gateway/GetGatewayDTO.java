package br.edu.utfpr.iotapi.dto.gateway;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GetGatewayDTO(
                long id,
                String nome,
                String descricao,
                String endereco) {

}
