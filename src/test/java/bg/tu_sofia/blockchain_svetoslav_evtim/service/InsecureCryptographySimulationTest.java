package bg.tu_sofia.blockchain_svetoslav_evtim.service;

import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InsecureCryptographySimulationTest {

    @Test
    void testEncrypt() throws Exception {
        InsecureCryptographySimulation simulation = new InsecureCryptographySimulation();
        String encryptedData = simulation.encrypt("SensitiveData");
        assertThat(encryptedData).isNotNull().isNotEmpty();
    }

    @Test
    public void testEncryptException() {
        InsecureCryptographySimulation simulation = new InsecureCryptographySimulation();
        assertThrows(GeneralSecurityException.class, () -> simulation.encrypt(null));
    }
}