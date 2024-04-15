package br.edu.utfpr.iotapi.dto.leitura;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CreateLeituraDTO (
        @NotNull long sensorId,
        @NotNull double valor,
        @NotNull Date data) {
}
