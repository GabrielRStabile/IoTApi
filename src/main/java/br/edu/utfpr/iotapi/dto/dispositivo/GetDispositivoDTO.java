package br.edu.utfpr.iotapi.dto.dispositivo;

public record GetDispositivoDTO(
    long id,
    String nome,
    String descricao,
    String local,
    String endereco,
    Long gatewayId) {
}