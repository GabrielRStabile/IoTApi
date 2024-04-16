package br.edu.utfpr.iotapi.dto;

public record GetDispositivoByGatewayIdDTO(
        long id,
        String nome,
        String descricao,
        String local,
        String endereco) {

}