package bg.tu_sofia.blockchain_svetoslav_evtim.service;

import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InsecureCryptographySimulationTest {

    @Test
    void testEncrypt() throws Exception {
        InsecureCryptographySimulation simulation = new InsecureCryptographySimulation();
        String encryptedData = simulation.encrypt("SensitiveData");
        assertThat(encryptedData).isNotNull().isNotEmpty();
    }

    @Test
    void testEncryptException() {
        InsecureCryptographySimulation simulation = new InsecureCryptographySimulation();
        assertThrows(GeneralSecurityException.class, () -> simulation.encrypt(null));
    }
}