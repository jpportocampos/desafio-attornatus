package io.github.jpportocampos.rest.dto;

import io.github.jpportocampos.domain.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesPessoaDTO {

    private Integer codigo;
    private String nome;
    private String dataNascimento;
    private List<InformacaoEnderecoDTO> enderecos;
}
