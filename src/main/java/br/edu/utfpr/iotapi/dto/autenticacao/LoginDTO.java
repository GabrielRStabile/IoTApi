package br.edu.utfpr.iotapi.dto.autenticacao;

import jakarta.validation.constraints.Email;

public record LoginDTO(@Email String email, String senha) {
}
