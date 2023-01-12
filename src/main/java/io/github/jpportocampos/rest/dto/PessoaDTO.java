package io.github.jpportocampos.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {

    private String nome;
    private LocalDate dataNascimento;
    private List<EnderecoDTO> enderecos;
}
