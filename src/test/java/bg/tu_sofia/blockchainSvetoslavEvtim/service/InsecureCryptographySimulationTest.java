package bg.tu_sofia.blockchainSvetoslavEvtim.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InsecureCryptographySimulationTest {

    @Test
    void testEncrypt() throws Exception {
        InsecureCryptographySimulation simulation = new InsecureCryptographySimulation();
        String encryptedData = simulation.encrypt("SensitiveData");
        assertThat(encryptedData).isNotNull();
        assertThat(encryptedData).isNotEmpty();
    }
}