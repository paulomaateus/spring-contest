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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.PauloMoreira.contest.model.ProcessoJudicial;
import com.PauloMoreira.contest.repository.ProcessoJudicialRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Testes de integração para a entidade {@link ProcessoJudicial}.
 *
 * Esta classe contém testes para verificar as operações CRUD e a manipulação de
 * exceções relacionadas ao {@link ProcessoJudicial}. Utiliza o {@link MockMvc}
 * para simular requisições HTTP e verificar as respostas da API.
 *
 * As anotações utilizadas são: - {@link SpringBootTest}: Carrega o contexto
 * completo da aplicação para testes de integração. -
 * {@link AutoConfigureMockMvc}: Configura o {@link MockMvc} para testes de
 * controladores REST. - {@link AutoConfigureTestDatabase}: Configura o banco de
 * dados de teste. Utiliza um banco de dados em memória para os testes. -
 * {@link Transactional}: Garante que cada teste é executado em uma transação
 * que é revertida após o término do teste. - {@link Rollback}: Garante que as
 * transações são revertidas após a execução do teste.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@Rollback
public class ProcessoJudicialIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProcessoJudicialRepository processoJudicialRepository;

    /**
     * Testa a criação de um novo processo judicial.
     *
     * Envia uma requisição POST para o endpoint "/processos" com um objeto
     * {@link ProcessoJudicial} válido e verifica se a resposta tem o status
     * HTTP 201 Created e contém os dados esperados.
     *
     * @throws Exception se ocorrer um erro durante o teste
     */
    @Test
    public void testCreateProcessoJudicial() throws Exception {
        ProcessoJudicial processo = new ProcessoJudicial();
        processo.setNumero("12345");
        processo.setDescricao("Descrição do Processo");
        processo.setStatus("Ativo");

        String jsonContent = objectMapper.writeValueAsString(processo);

        mockMvc.perform(MockMvcRequestBuilders.post("/processos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numero").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Descrição do Processo"));
    }

    /**
     * Testa a criação de um processo judicial com dados inválidos.
     *
     * Envia uma requisição POST para o endpoint "/processos" com dados
     * inválidos (como número do processo nulo ou vazio) e verifica se a
     * resposta tem o status HTTP 400 Bad Request e contém a mensagem de erro
     * apropriada.
     *
     * @throws Exception se ocorrer um erro durante o teste
     */
    @Test
    public void testCreateProcessoJudicialException() throws Exception {
        ProcessoJudicial processo1 = new ProcessoJudicial();
        processo1.setDescricao("Descrição 1");
        processo1.setStatus("Ativo");

        ProcessoJudicial processo2 = new ProcessoJudicial();
        processo2.setNumero("");
        processo2.setDescricao("Descrição 1");
        processo2.setStatus("Ativo");

        String jsonContent1 = objectMapper.writeValueAsString(processo1);
        String jsonContent2 = objectMapper.writeValueAsString(processo2);

        mockMvc.perform(MockMvcRequestBuilders.post("/processos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent1))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("O número do processo não pode ser nulo nem vazio."));

        mockMvc.perform(MockMvcRequestBuilders.post("/processos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent2))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("O número do processo não pode ser nulo nem vazio."));
    }

    /**
     * Testa a criação de um processo judicial com número duplicado.
     *
     * Envia duas requisições POST para o endpoint "/processos" com o mesmo
     * número de processo e verifica se a primeira requisição cria o processo
     * com sucesso e a segunda gera um status HTTP 409 Conflict com a mensagem
     * de erro de duplicidade.
     *
     * @throws Exception se ocorrer um erro durante o teste
     */
    @Test
    public void testCreateProcessoJudicialDuplicate() throws Exception {
        ProcessoJudicial processo = new ProcessoJudicial();
        processo.setNumero("123456");
        processo.setDescricao("Descrição do Processo");
        processo.setStatus("Ativo");

        mockMvc.perform(MockMvcRequestBuilders.post("/processos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(processo)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/processos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(processo)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Duplicidade de processos. Já existe um processo salvo com esse número."));
    }

    /**
     * Testa a listagem de processos quando não há nenhum no banco de dados.
     *
     * Envia uma requisição GET para o endpoint "/processos" e verifica se a
     * resposta tem o status HTTP 200 OK e se a resposta contém uma lista vazia
     * de processos.
     *
     * @throws Exception se ocorrer um erro durante o teste
     */
    @Test
    public void testListProcessosVazios() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/processos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.size").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.number").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalPages").value(0));
    }

    /**
     * Testa a listagem de processos quando há itens no banco de dados.
     *
     * Adiciona dois processos no banco de dados e envia uma requisição GET para
     * o endpoint "/processos". Verifica se a resposta tem o status HTTP 200 OK
     * e se a resposta contém os dados dos processos adicionados.
     *
     * @throws Exception se ocorrer um erro durante o teste
     */
    @Test
    public void testListProcessosComItens() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders.get("/processos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].numero").value("123456"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].descricao").value("Descrição 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].status").value("Ativo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].numero").value("010123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].descricao").value("Descrição 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].status").value("Ativo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.size").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.number").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalPages").value(1));
    }

    /**
     * Testa a exclusão de um processo judicial.
     *
     * Adiciona três processos no banco de dados e envia uma requisição DELETE
     * para o endpoint "/processos/{numero}" para excluir um processo
     * específico. Verifica se a exclusão é bem-sucedida com o status HTTP 204
     * No Content e se o processo foi removido da lista.
     *
     * @throws Exception se ocorrer um erro durante o teste
     */
    @Test
    public void testDeleteProcesso() throws Exception {
        ProcessoJudicial processo1 = new ProcessoJudicial();
        processo1.setNumero("123456");
        processo1.setDescricao("Descrição 1");
        processo1.setStatus("Ativo");

        ProcessoJudicial processo2 = new ProcessoJudicial();
        processo2.setNumero("010123");
        processo2.setDescricao("Descrição 2");
        processo2.setStatus("Ativo");

        ProcessoJudicial processo3 = new ProcessoJudicial();
        processo3.setNumero("00000");
        processo3.setDescricao("Deletar Processo");
        processo3.setStatus("Ativo");

        processoJudicialRepository.save(processo1);
        processoJudicialRepository.save(processo2);
        processoJudicialRepository.save(processo3);

        mockMvc.perform(MockMvcRequestBuilders.delete("/processos/{numero}", "00000"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/processos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.size").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.number").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalPages").value(1));
    }

    /**
     * Testa a tentativa de exclusão de um processo que não existe.
     *
     * Adiciona um processo no banco de dados e envia uma requisição DELETE para
     * o endpoint "/processos/{numero}" tentando excluir um processo que não
     * existe. Verifica se a resposta tem o status HTTP 404 Not Found e contém a
     * mensagem de erro apropriada.
     *
     * @throws Exception se ocorrer um erro durante o teste
     */
    @Test
    public void testDeleteNotFoundProcesso() throws Exception {
        ProcessoJudicial processo1 = new ProcessoJudicial();
        processo1.setNumero("123456");
        processo1.setDescricao("Descrição 1");
        processo1.setStatus("Ativo");

        processoJudicialRepository.save(processo1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/processos/{numero}", "00000"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Processo inexistente. O número do processo ao qual se quer deletar não existe no sistema."));
    }

}
