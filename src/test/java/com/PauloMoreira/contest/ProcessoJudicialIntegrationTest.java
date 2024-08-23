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
        String jsonContent2 = objectMapper.writeValueAsString(processo1);

        mockMvc.perform(MockMvcRequestBuilders.post("/processos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent1))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("O numerro do processo não pode ser nulo nem vazio."));

        mockMvc.perform(MockMvcRequestBuilders.post("/processos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent2))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("O numerro do processo não pode ser nulo nem vazio."));
    }

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Duplicidade de processos. Já existe um processo salvo com esse número"));
    }

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
