package br.edu.utfpr.iotapi.models;

import lombok.Data;

@Data
public class JWTToken {
    private String token;
    private String refreshToken;
}
