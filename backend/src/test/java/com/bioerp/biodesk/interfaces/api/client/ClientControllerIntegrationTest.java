package com.bioerp.biodesk.interfaces.api.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateClientAndUnitAndFetchThem() throws Exception {
        String clientPayload = objectMapper.writeValueAsString(new CreateClientRequest(
                "Bio Corp",
                "Bio Corp",
                "04.252.011/0001-10",
                "Energia",
                "active",
                "João Gestor",
                "joao@bioerp.com",
                "11922223333",
                "Cliente de integração"
        ));

        MvcResult clientResult = mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientPayload))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode clientNode = objectMapper.readTree(clientResult.getResponse().getContentAsString());
        String clientId = clientNode.get("id").asText();
        assertThat(clientNode.get("cnpj").asText()).isEqualTo("04252011000110");

        String unitPayload = objectMapper.writeValueAsString(new CreateUnitRequest(
                "Filial Norte",
                "27865757000102",
                "Avenida Central, 123",
                "São Paulo",
                "SP",
                "Operação integrada",
                ""
        ));

        MvcResult unitResult = mockMvc.perform(post("/api/clients/" + clientId + "/units")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(unitPayload))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode unitNode = objectMapper.readTree(unitResult.getResponse().getContentAsString());
        String unitId = unitNode.get("id").asText();
        assertThat(unitNode.get("clientId").asText()).isEqualTo(clientId);

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk());

        MvcResult unitsResult = mockMvc.perform(get("/api/clients/" + clientId + "/units"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode unitsArray = objectMapper.readTree(unitsResult.getResponse().getContentAsString());
        assertThat(unitsArray).isNotEmpty();

        mockMvc.perform(get("/api/units/" + unitId))
                .andExpect(status().isOk());
    }
}
