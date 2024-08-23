package com.PauloMoreira.contest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.PauloMoreira.contest.dto.ReuDTO;
import com.PauloMoreira.contest.model.ProcessoJudicial;
import com.PauloMoreira.contest.repository.ProcessoJudicialRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Testes de integração para a entidade Reu no contexto da API de processos
 * judiciais.
 *
 * Configura um ambiente de teste com um banco de dados em memória e executa
 * testes para validar a criação de réus, a validação de dados e a listagem de
 * processos com réus associados.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@Rollback
public class ReuIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProcessoJudicialRepository processoJudicialRepository;

    /**
     * Testa a criação de um réu com dados válidos.
     *
     * Cria um processo judicial, salva no banco de dados e então envia uma
     * requisição POST para o endpoint "/reu" com dados válidos do réu. Verifica
     * se a resposta tem o status HTTP 201 Created e se os dados do réu são
     * retornados corretamente.
     *
     * @throws Exception se ocorrer um erro durante o teste
     */
    @Test
    public void testCreateReu() throws Exception {
        ProcessoJudicial processo = new ProcessoJudicial();
        processo.setNumero("12345");
        processo.setDescricao("Descrição do Processo");
        processo.setStatus("Ativo");

        processoJudicialRepository.save(processo);

        ReuDTO reuDTO = new ReuDTO();
        reuDTO.setCpf("12345678900");
        reuDTO.setNome("Reu teste");
        reuDTO.setNumeroProcesso("12345");

        String jsonContent = objectMapper.writeValueAsString(reuDTO);

        mockMvc.perform(post("/reu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").value("12345678900"))
                .andExpect(jsonPath("$.nome").value("Reu teste"))
                .andExpect(jsonPath("$.numeroProcesso").value("12345"));
    }

    /**
     * Testa a criação de um réu com um número de processo que não existe.
     *
     * Cria um processo judicial, salva no banco de dados e então tenta criar um
     * réu com um número de processo que não está cadastrado. Verifica se a
     * resposta tem o status HTTP 404 Not Found e contém a mensagem de erro
     * apropriada.
     *
     * @throws Exception se ocorrer um erro durante o teste
     */
    @Test
    public void testCreateReuProcessoNotFound() throws Exception {
        ProcessoJudicial processo = new ProcessoJudicial();
        processo.setNumero("12345");
        processo.setDescricao("Descrição do Processo");
        processo.setStatus("Ativo");

        processoJudicialRepository.save(processo);

        ReuDTO reuDTO = new ReuDTO();
        reuDTO.setCpf("12345678900");
        reuDTO.setNome("Reu teste");
        reuDTO.setNumeroProcesso("00000");

        String jsonContent = objectMapper.writeValueAsString(reuDTO);

        mockMvc.perform(post("/reu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Processo inexistente. O processo do réu não está cadastrado no sistema."));
    }

    /**
     * Testa a criação de um réu com CPF inválido.
     *
     * Tenta criar um réu com um CPF inválido (com menos de 11 dígitos) e também
     * tenta criar um réu sem fornecer o CPF. Verifica se a resposta tem o
     * status HTTP 400 Bad Request e contém a mensagem de erro apropriada para o
     * CPF inválido.
     *
     * @throws Exception se ocorrer um erro durante o teste
     */
    @Test
    public void testCreateReuInvalidCpf() throws Exception {

        ReuDTO reuDTO = new ReuDTO();
        reuDTO.setCpf("1234567890");
        reuDTO.setNome("Reu teste");
        reuDTO.setNumeroProcesso("00000");

        ReuDTO reuDTONoneCpf = new ReuDTO();
        reuDTONoneCpf.setNome("Reu teste");
        reuDTONoneCpf.setNumeroProcesso("00000");

        String jsonContent1 = objectMapper.writeValueAsString(reuDTO);
        String jsonContent2 = objectMapper.writeValueAsString(reuDTONoneCpf);

        mockMvc.perform(post("/reu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Número de CPF inválido."));

        mockMvc.perform(post("/reu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent2))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Número de CPF inválido."));
    }

    /**
     * Testa a listagem de processos com réus associados.
     *
     * Cria dois processos judiciais e salva no banco de dados. Em seguida, cria
     * um réu associado a um dos processos e verifica se a listagem de processos
     * retorna os dados corretos, incluindo o CPF do réu no processo associado.
     * Verifica se a resposta tem o status HTTP 200 OK e se os dados dos
     * processos e do réu são retornados corretamente.
     *
     * @throws Exception se ocorrer um erro durante o teste
     */
    @Test
    public void testListProcessosWithReu() throws Exception {
        ProcessoJudicial processo1 = new ProcessoJudicial();
        processo1.setNumero("123456");
        processo1.setDescricao("Descrição 1");
        processo1.setStatus("Ativo");

        ProcessoJudicial processo2 = new ProcessoJudicial();
        processo2.setNumero("010123");
        processo2.setDescricao("Descrição 2");
        processo2.setStatus("Ativo");

        processoJudicialRepository.save(processo1);
        processoJudicialRepository.save(processo2);

        ReuDTO reuDTO = new ReuDTO();
        reuDTO.setCpf("12345678900");
        reuDTO.setNome("Reu teste");
        reuDTO.setNumeroProcesso("010123");

        String jsonContent = objectMapper.writeValueAsString(reuDTO);

        mockMvc.perform(post("/reu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get("/processos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].numero").value("123456"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].descricao").value("Descrição 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].status").value("Ativo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].cpfReu").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].numero").value("010123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].descricao").value("Descrição 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].status").value("Ativo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].cpfReu").value("12345678900"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.size").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.number").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalPages").value(1));
    }
}
