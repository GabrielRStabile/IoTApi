package br.edu.utfpr.iotapi.dto.atuador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAtuadorDTO(@NotBlank @Size(min = 2) String nome) {

}
