package com.bioerp.biodesk.interfaces.api.license;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bioerp.biodesk.interfaces.api.client.CreateClientRequest;
import com.bioerp.biodesk.interfaces.api.client.CreateUnitRequest;
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
class LicenseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateLicenseListItAndExposeCalculatedStatus() throws Exception {
        String clientPayload = objectMapper.writeValueAsString(new CreateClientRequest("Licenses Corp", "40.688.134/0001-61"));

        MvcResult clientResult = mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientPayload))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode clientNode = objectMapper.readTree(clientResult.getResponse().getContentAsString());
        String clientId = clientNode.get("id").asText();

        String unitPayload = objectMapper.writeValueAsString(new CreateUnitRequest("Unidade Central", "40436654535889"));

        MvcResult unitResult = mockMvc.perform(post("/api/clients/" + clientId + "/units")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(unitPayload))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode unitNode = objectMapper.readTree(unitResult.getResponse().getContentAsString());
        String unitId = unitNode.get("id").asText();

        CreateEnvironmentalLicenseRequest licenseRequest = new CreateEnvironmentalLicenseRequest(
                "Licença de Operação",
                "IBAMA",
                LocalDate.now(),
                LocalDate.now().plusYears(5),
                120,
                false,
                null,
                Set.of()
        );

        String licensePayload = objectMapper.writeValueAsString(licenseRequest);

        MvcResult licenseResult = mockMvc.perform(post("/api/units/" + unitId + "/licenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(licensePayload))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode licenseNode = objectMapper.readTree(licenseResult.getResponse().getContentAsString());
        String licenseId = licenseNode.get("id").asText();

        assertThat(licenseNode.get("unitId").asText()).isEqualTo(unitId);
        assertThat(licenseNode.get("clientId").asText()).isEqualTo(clientId);
        assertThat(licenseNode.get("status").asText()).isEqualTo("VIGENTE");

        MvcResult listResult = mockMvc.perform(get("/api/licenses"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode listNode = objectMapper.readTree(listResult.getResponse().getContentAsString());
        assertThat(listNode.isArray()).isTrue();
        assertThat(listNode).anyMatch(node -> node.get("id").asText().equals(licenseId));

        MvcResult filteredResult = mockMvc.perform(get("/api/licenses").param("unitId", unitId))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode filteredArray = objectMapper.readTree(filteredResult.getResponse().getContentAsString());
        assertThat(filteredArray).allMatch(node -> node.get("unitId").asText().equals(unitId));

        MvcResult byIdResult = mockMvc.perform(get("/api/licenses/" + licenseId))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode byIdNode = objectMapper.readTree(byIdResult.getResponse().getContentAsString());
        assertThat(byIdNode.get("status").asText()).isEqualTo("VIGENTE");
    }
}
