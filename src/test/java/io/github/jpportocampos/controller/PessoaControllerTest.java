package io.github.jpportocampos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jpportocampos.domain.entity.Endereco;
import io.github.jpportocampos.domain.entity.Pessoa;
import io.github.jpportocampos.rest.dto.EnderecoDTO;
import io.github.jpportocampos.rest.dto.PessoaDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Pessoa pessoa;

    @Mock
    private Endereco endereco;

    @Test
    void cadastrarPessoaTest() throws Exception {
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setNome("João Pedro");
        pessoa.setDataNascimento(LocalDate.parse("1999-05-14"));

        EnderecoDTO endereco = new EnderecoDTO(1, "Rua Marquês de Abrantes",
                22230061, 212, "Rio de Janeiro", true);

        List<EnderecoDTO> enderecos = Collections.singletonList(endereco);

        pessoa.setEnderecos(enderecos);

        String cadastroJSON = objectMapper.writeValueAsString(pessoa);
        this.mockMvc.perform(post("/api/pessoas").contentType(MediaType.APPLICATION_JSON).content(cadastroJSON))
                .andExpect(status().is(201));
    }

    @Test
    void atualizarPessoaTest() throws Exception {
        cadastrarPessoaTest();

        EnderecoDTO endereco = new EnderecoDTO(1, "Rua Paissandu",
                22210085, 93, "Rio de Janeiro", false);

        List<EnderecoDTO> enderecos = Collections.singletonList(endereco);

        String pessoaJSON = objectMapper.writeValueAsString(new PessoaDTO("João Pedro", LocalDate.parse("1999-05-14"), enderecos));

        this.mockMvc.perform(put("/api/pessoas/1").contentType(MediaType.APPLICATION_JSON).content(pessoaJSON)).andExpect(status().is(200));
    }

    @Test
    void atualizarPessoaInvalidaTest() throws Exception {
        String pessoaJSON = objectMapper.writeValueAsString(new Pessoa());
        this.mockMvc.perform(put("/api/pessoas/0").contentType(MediaType.APPLICATION_JSON).content(pessoaJSON)).andExpect(status().is(400))
                .andExpect(result -> assertEquals("A pessoa solicitada não existe", result.getResolvedException().getMessage()));
    }

    @Test
    void getPessoaTest() throws Exception {
        this.mockMvc.perform(get("/api/pessoas/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("14/05/1999")));
    }

    @Test
    void getPessoaDadosInvalidosTest() throws Exception {
        this.mockMvc.perform(get("/api/pessoas/0")).andExpect(status().is(404))
                .andExpect(result -> assertEquals("404 NOT_FOUND \"Pessoa não encontrada.\"", result.getResolvedException().getMessage()));
    }

    @Test
    void getPessoasTest() throws Exception {
        this.mockMvc.perform(get("/api/pessoas/")).andExpect(status().isOk())
                .andExpect(content().string(containsString("14/05/1999")));
    }

    @Test
    void getPessoaEnderecosTest() throws Exception {
        this.mockMvc.perform(get("/api/pessoas/enderecos/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Rua")));
    }

    @Test
    void getPessoaEnderecosDadosInvalidosTest() throws Exception {
        this.mockMvc.perform(get("/api/pessoas/enderecos/0")).andExpect(status().is(404))
                .andExpect(result -> assertEquals("404 NOT_FOUND \"Pessoa não encontrada.\"", result.getResolvedException().getMessage()));
    }
}
