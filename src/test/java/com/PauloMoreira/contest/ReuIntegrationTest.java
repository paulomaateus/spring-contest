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
import com.PauloMoreira.contest.repository.ReuRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private ReuRepository reuRepository;

    @Autowired
    private ProcessoJudicialRepository processoJudicialRepository;

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
