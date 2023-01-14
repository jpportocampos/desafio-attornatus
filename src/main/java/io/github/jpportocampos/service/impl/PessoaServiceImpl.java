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
    @Transactional
    public Pessoa salvar(Integer id, PessoaDTO dto) {
        Pessoa pessoa = repository
                .findById(id)
                .orElseThrow(() -> new RegraNegocioException("A pessoa solicitada não existe"));
        pessoa.setId(id);
        pessoa.setNome(dto.getNome());
        pessoa.setDataNascimento(dto.getDataNascimento());

        List<Endereco> enderecos = alterarEnderecos(pessoa, dto.getEnderecos());
        repository.save(pessoa);
        enderecosRepository.saveAll(enderecos);
        pessoa.setEnderecos(enderecos);
        return pessoa;
    }

    @Override
    public Optional<Pessoa> obterPessoaCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    public List<Pessoa> obterTodos() {
        List<Pessoa> pessoas = repository.findAll();

        if (pessoas.isEmpty()) {
            throw new RegraNegocioException("Não existem pessoas salvas.");
        }

        return pessoas;
    }

    @Override
    public List<Endereco> obterEnderecos(Integer id) {
        List<Endereco> enderecos = repository.findByIdFetchEnderecos(id);

        if (enderecos.isEmpty()) {
            throw new RegraNegocioException("Não existem endereços salvos");
        }

        return enderecos;
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
                    if (dto.getIsPrincipal()) {
                        pessoa.setEnderecoPrincipal(endereco);
                    }
                    return endereco;
                }).collect(Collectors.toList());
    }

    private List<Endereco> alterarEnderecos(Pessoa pessoa, List<EnderecoDTO> enderecos) {
        if (enderecos.isEmpty()) {
            return pessoa.getEnderecos();
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
                    if (dto.getIsPrincipal()) {
                        pessoa.setEnderecoPrincipal(endereco);
                    }
                    return endereco;
                }).collect(Collectors.toList());
    }
}
