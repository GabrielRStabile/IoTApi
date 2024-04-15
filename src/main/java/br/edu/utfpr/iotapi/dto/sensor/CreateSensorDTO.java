package br.edu.utfpr.iotapi.dto.sensor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSensorDTO(
        @NotBlank
        @Size(min = 2)
        String nome,
        @NotBlank
        @Size(min = 2)
        String tipo
) {
}
