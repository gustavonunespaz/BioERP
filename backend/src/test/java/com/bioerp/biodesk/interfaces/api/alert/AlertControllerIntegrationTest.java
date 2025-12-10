package com.bioerp.biodesk.interfaces.api.alert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bioerp.biodesk.core.application.GenerateAlertsUseCase;
import com.bioerp.biodesk.interfaces.api.client.CreateClientRequest;
import com.bioerp.biodesk.interfaces.api.client.CreateUnitRequest;
import com.bioerp.biodesk.interfaces.api.license.CreateEnvironmentalLicenseRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Set;
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
class AlertControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GenerateAlertsUseCase generateAlertsUseCase;

    @Test
    void shouldListAlertsAndMarkThemAsRead() throws Exception {
        String clientPayload = objectMapper.writeValueAsString(new CreateClientRequest(
                "Alert Corp",
                "Alert Corp",
                "11.222.333/0001-81",
                "Energia",
                "active",
                "Maria Gestora",
                "gestora@alert.com",
                "11911110000",
                "Cliente para testes de alertas"
        ));

        MvcResult clientResult = mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String clientId = objectMapper.readTree(clientResult.getResponse().getContentAsString()).get("id").asText();

        String unitPayload = objectMapper.writeValueAsString(new CreateUnitRequest(
                "Unidade Centro",
                "19131243000197",
                "Rua Alfa, 50",
                "São Paulo",
                "SP",
                "Operação teste",
                ""
        ));

        MvcResult unitResult = mockMvc.perform(post("/api/clients/" + clientId + "/units")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(unitPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String unitId = objectMapper.readTree(unitResult.getResponse().getContentAsString()).get("id").asText();

        CreateEnvironmentalLicenseRequest licenseRequest = new CreateEnvironmentalLicenseRequest(
                "Licença CRL",
                "IBAMA",
                LocalDate.now(),
                LocalDate.now().plusDays(20),
                120,
                false,
                null,
                Set.of()
        );

        mockMvc.perform(post("/api/units/" + unitId + "/licenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(licenseRequest)))
                .andExpect(status().isCreated());

        generateAlertsUseCase.handle();

        MvcResult listResult = mockMvc.perform(get("/api/alerts"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode alertsNode = objectMapper.readTree(listResult.getResponse().getContentAsString());
        assertThat(alertsNode.isArray()).isTrue();
        assertThat(alertsNode).isNotEmpty();

        String alertId = alertsNode.get(0).get("id").asText();

        MvcResult readResult = mockMvc.perform(patch("/api/alerts/" + alertId + "/read"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode readNode = objectMapper.readTree(readResult.getResponse().getContentAsString());
        assertThat(readNode.get("read").asBoolean()).isTrue();
    }
}
