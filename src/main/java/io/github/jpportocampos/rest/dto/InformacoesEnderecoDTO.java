package io.github.jpportocampos.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesEnderecoDTO {

    private String logradouro;
    private Integer cep;
    private Integer numero;
    private String cidade;
    private Boolean isPrincipal;
}
