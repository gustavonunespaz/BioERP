package com.bioerp;

import com.bioerp.biodesk.BioDeskApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = BioDeskApplication.class)
@ActiveProfiles("test")
class BioErpApplicationTests {

    @Test
    void contextLoads() {
    }
}
