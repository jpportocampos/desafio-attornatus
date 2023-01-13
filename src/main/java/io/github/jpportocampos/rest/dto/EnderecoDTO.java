package io.github.jpportocampos.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {

    private Integer pessoa;
    private String logradouro;
    private Integer cep;
    private Integer numero;
    private String cidade;
    private Boolean isPrincipal;
}
