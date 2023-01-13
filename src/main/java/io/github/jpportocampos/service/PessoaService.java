package io.github.jpportocampos.service;

import io.github.jpportocampos.domain.entity.Endereco;
import io.github.jpportocampos.domain.entity.Pessoa;
import io.github.jpportocampos.rest.dto.PessoaDTO;

import java.util.List;
import java.util.Optional;

public interface PessoaService {
    Pessoa salvar(PessoaDTO dto);

    Pessoa salvar(Integer id, PessoaDTO dto);

    Optional<Pessoa> obterPessoaCompleto(Integer id);

    List<Pessoa> obterTodos();

    List<Endereco> obterEnderecos(Integer id);
}
