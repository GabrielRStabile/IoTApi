package br.edu.utfpr.iotapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePessoaDTO(
                @NotBlank @Size(min = 2, message = "O nome deve ter no mínimo 2 caracteres") String nome,
                @NotBlank @Email(message = "Email inválido") String email,
                @NotBlank @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres") String senha) {
}
