package br.edu.utfpr.iotapi.dto;

import jakarta.validation.constraints.Email;

public record DeletePessoaDTO(
    @Email(message = "Email inválido") String email,
    String senha) {

}