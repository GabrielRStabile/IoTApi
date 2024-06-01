package br.edu.utfpr.iotapi.dto.sensor;

public record GetSensorDTO(
    long id,
    String nome,
    String tipo,
    Long dispositivoId) {

}
