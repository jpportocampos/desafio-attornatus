package io.github.jpportocampos.service;

import io.github.jpportocampos.domain.entity.Pessoa;
import io.github.jpportocampos.rest.dto.PessoaDTO;

import java.util.Optional;

public interface PessoaService {
    Pessoa salvar(PessoaDTO dto);

    Optional<Pessoa> obterPessoaCompleto(Integer id);
}
