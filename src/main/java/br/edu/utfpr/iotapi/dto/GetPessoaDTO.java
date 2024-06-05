package br.edu.utfpr.iotapi.dto;

import br.edu.utfpr.iotapi.models.FuncaoPessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class GetPessoaDTO {
    private long id;
    private String nome;
    private String email;
    private FuncaoPessoa funcao;
}
