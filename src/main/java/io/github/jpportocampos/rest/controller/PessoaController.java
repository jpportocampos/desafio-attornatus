package io.github.jpportocampos.rest.controller;

import io.github.jpportocampos.domain.entity.Endereco;
import io.github.jpportocampos.domain.entity.Pessoa;
import io.github.jpportocampos.rest.dto.InformacaoEnderecoDTO;
import io.github.jpportocampos.rest.dto.InformacoesPessoaDTO;
import io.github.jpportocampos.rest.dto.PessoaDTO;
import io.github.jpportocampos.service.PessoaService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PessoaDTO dto) {
        Pessoa pessoa = service.salvar(dto);
        return pessoa.getId();
    }

    @GetMapping("{id}")
    public InformacoesPessoaDTO getById(@PathVariable Integer id) {
        return service
                .obterPessoaCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada."));
    }

    @PutMapping("{id}")
    public void update(@PathVariable Integer id, @RequestBody PessoaDTO dto) {
        Pessoa pessoa = service.salvar(id, dto);
    }

    @GetMapping
    public List<InformacoesPessoaDTO> listarPessoas() {
        return service
                .obterTodos()
                .stream()
                .map(p -> converter(p)).collect(Collectors.toList());
    }

    private InformacoesPessoaDTO converter(Pessoa pessoa) {
        return InformacoesPessoaDTO
                .builder()
                .codigo(pessoa.getId())
                .nome(pessoa.getNome())
                .dataNascimento(pessoa.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .enderecos(converter(pessoa.getEnderecos()))
                .build();
    }

    private List<InformacaoEnderecoDTO> converter(List<Endereco> enderecos) {
        if(CollectionUtils.isEmpty(enderecos)) {
            return Collections.emptyList();
        }

        return enderecos.stream().map(
                endereco -> InformacaoEnderecoDTO
                        .builder()
                        .logradouro(endereco.getLogradouro())
                        .cep(endereco.getCep())
                        .numero(endereco.getNumero())
                        .cidade(endereco.getCidade())
                        .isPrincipal(endereco.getPessoa().getEnderecoPrincipal() == endereco)
                        .build()
        ).collect(Collectors.toList());
    }
}
