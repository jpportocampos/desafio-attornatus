package io.github.jpportocampos.service;

import io.github.jpportocampos.domain.entity.Endereco;
import io.github.jpportocampos.domain.entity.Pessoa;
import io.github.jpportocampos.domain.repository.Enderecos;
import io.github.jpportocampos.domain.repository.Pessoas;
import io.github.jpportocampos.rest.dto.EnderecoDTO;
import io.github.jpportocampos.rest.dto.PessoaDTO;
import io.github.jpportocampos.service.impl.PessoaServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PessoaServiceTest {

    @InjectMocks
    private PessoaServiceImpl pessoaService;

    @Mock
    private Pessoas pessoaRepository;

    @Mock
    private Enderecos enderecoRepository;

    @Mock
    private Endereco endereco;

    @Mock
    private Pessoa pessoa;

    @Test
    public void cadastrarPessoaNullTest() {
        assertEquals(null, this.pessoaService.salvar(null));
    }

    @Test
    public void cadastrarPessoaTest() {
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setNome("João Pedro");
        pessoa.setDataNascimento(LocalDate.parse("1999-05-14"));

        EnderecoDTO endereco = new EnderecoDTO(1, "Rua Marquês de Abrantes",
                22230061, 212, "Rio de Janeiro", true);

        List<EnderecoDTO> enderecos = Collections.singletonList(endereco);

        pessoa.setEnderecos(enderecos);

        Mockito.when(this.pessoaRepository.save(ArgumentMatchers.any(Pessoa.class))).thenReturn(null);

        assertEquals(pessoa.getNome(), this.pessoaService.salvar(pessoa).getNome());
    }

    @Test
    public void editarPessoaTest() {
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setNome("João Pedro");
        pessoa.setDataNascimento(LocalDate.parse("1999-05-14"));

        EnderecoDTO endereco = new EnderecoDTO(1, "Rua Marquês de Abrantes",
                22230061, 212, "Rio de Janeiro", true);

        List<EnderecoDTO> enderecos = Collections.singletonList(endereco);

        pessoa.setEnderecos(enderecos);

        PessoaDTO pessoaAtualizada = new PessoaDTO();
        pessoaAtualizada.setNome("Manuela");
        pessoaAtualizada.setEnderecos(Collections.emptyList());

        Mockito.when(this.pessoaRepository.save(ArgumentMatchers.any(Pessoa.class))).thenReturn(null);
        Mockito.when(this.pessoaRepository.findById(ArgumentMatchers.any(Integer.class))).thenReturn(Optional.of(new Pessoa()));

        this.pessoaService.salvar(pessoa);

        assertEquals(pessoaAtualizada.getNome(), this.pessoaService.salvar(1, pessoaAtualizada).getNome());
    }

    @Test
    public void obterPessoaCompletoTest() {
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setNome("João Pedro");
        pessoa.setDataNascimento(LocalDate.parse("1999-05-14"));

        EnderecoDTO endereco = new EnderecoDTO(1, "Rua Marquês de Abrantes",
                22230061, 212, "Rio de Janeiro", true);

        List<EnderecoDTO> enderecos = Collections.singletonList(endereco);

        pessoa.setEnderecos(enderecos);

        Mockito.when(this.pessoaRepository.save(ArgumentMatchers.any(Pessoa.class))).thenReturn(null);

        Pessoa pessoaSalva = this.pessoaService.salvar(pessoa);

        Mockito.when(this.pessoaRepository.findByIdFetchItens(ArgumentMatchers.any(Integer.class))).thenReturn(Optional.of(pessoaSalva));

        Optional<Pessoa> pessoaObtida = this.pessoaService.obterPessoaCompleto(1);

        assertEquals(pessoa.getNome(), pessoaObtida.get().getNome());
    }

    @Test
    public void obterTodosTest() {
        PessoaDTO pessoa1 = new PessoaDTO();
        pessoa1.setNome("João Pedro");
        pessoa1.setDataNascimento(LocalDate.parse("1999-05-14"));

        EnderecoDTO endereco1 = new EnderecoDTO(1, "Rua Marquês de Abrantes",
                22230061, 212, "Rio de Janeiro", true);

        List<EnderecoDTO> enderecos1 = Collections.singletonList(endereco1);

        pessoa1.setEnderecos(enderecos1);

        PessoaDTO pessoa2 = new PessoaDTO();
        pessoa2.setNome("Manuela");
        pessoa2.setDataNascimento(LocalDate.parse("2000-08-22"));

        EnderecoDTO endereco2 = new EnderecoDTO(3, "Rua Marquês de Abrantes",
                22230061, 212, "Rio de Janeiro", true);

        List<EnderecoDTO> enderecos2 = Collections.singletonList(endereco2);

        pessoa2.setEnderecos(enderecos2);

        Mockito.when(this.pessoaRepository.save(ArgumentMatchers.any(Pessoa.class))).thenReturn(null);

        Pessoa pessoaSalva1 = this.pessoaService.salvar(pessoa1);
        Pessoa pessoaSalva2 = this.pessoaService.salvar(pessoa2);

        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(0, pessoaSalva1);
        pessoas.add(1, pessoaSalva2);

        Mockito.when(this.pessoaRepository.findAll()).thenReturn(pessoas);

        List<Pessoa> pessoasObtidas = this.pessoaService.obterTodos();

        assertEquals(pessoas, pessoasObtidas);
    }

    @Test
    public void obterEnderecosTest() {
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setNome("João Pedro");
        pessoa.setDataNascimento(LocalDate.parse("1999-05-14"));

        EnderecoDTO endereco = new EnderecoDTO(1, "Rua Marquês de Abrantes",
                22230061, 212, "Rio de Janeiro", true);

        List<EnderecoDTO> enderecos = Collections.singletonList(endereco);

        pessoa.setEnderecos(enderecos);

        Mockito.when(this.pessoaRepository.save(ArgumentMatchers.any(Pessoa.class))).thenReturn(null);

        Pessoa pessoaSalva = this.pessoaService.salvar(pessoa);

        Mockito.when(this.pessoaRepository.findByIdFetchEnderecos(ArgumentMatchers.any(Integer.class))).thenReturn(pessoaSalva.getEnderecos());

        List<Endereco> enderecosObtidos = this.pessoaService.obterEnderecos(1);

        assertEquals(pessoaSalva.getEnderecos(), enderecosObtidos);
    }
}
