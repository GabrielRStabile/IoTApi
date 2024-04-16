package br.edu.utfpr.iotapi.dto.gateway;

import java.util.List;

import br.edu.utfpr.iotapi.models.Dispositivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GetGatewayDTO(
                long id,
                String nome,
                String descricao,
                String endereco) {

}
