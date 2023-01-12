package io.github.jpportocampos.service.impl;

import io.github.jpportocampos.domain.entity.Endereco;
import io.github.jpportocampos.domain.entity.Pessoa;
import io.github.jpportocampos.domain.repository.Enderecos;
import io.github.jpportocampos.domain.repository.Pessoas;
import io.github.jpportocampos.exception.RegraNegocioException;
import io.github.jpportocampos.rest.dto.EnderecoDTO;
import io.github.jpportocampos.rest.dto.PessoaDTO;
import io.github.jpportocampos.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {

    private final Pessoas repository;
    private final Enderecos enderecosRepository;

    @Override
    @Transactional
    public Pessoa salvar(PessoaDTO dto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.getNome());
        pessoa.setDataNascimento(dto.getDataNascimento());

        List<Endereco> enderecos = converterEnderecos(pessoa, dto.getEnderecos());
        repository.save(pessoa);
        enderecosRepository.saveAll(enderecos);
        pessoa.setEnderecos(enderecos);
        return pessoa;
    }

    @Override
    public Optional<Pessoa> obterPessoaCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    private List<Endereco> converterEnderecos(Pessoa pessoa, List<EnderecoDTO> enderecos) {
        if (enderecos.isEmpty()) {
            throw new RegraNegocioException("Uma pessoa precisa possuir pelo menos um endereço.");
        }

        return enderecos
                .stream()
                .map(dto -> {
                    Endereco endereco = new Endereco();
                    endereco.setPessoa(pessoa);
                    endereco.setCep(dto.getCep());
                    endereco.setCidade(dto.getCidade());
                    endereco.setNumero(dto.getNumero());
                    endereco.setLogradouro(dto.getLogradouro());
                    return endereco;
                }).collect(Collectors.toList());
    }
}
