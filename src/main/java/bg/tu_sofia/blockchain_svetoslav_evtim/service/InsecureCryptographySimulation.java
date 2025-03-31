package bg.tu_sofia.blockchain_svetoslav_evtim.service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;
import java.util.Base64;

public class InsecureCryptographySimulation {

    public String encrypt(String data) throws GeneralSecurityException {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES"); // DES is considered insecure
            SecretKey secretKey = keyGen.generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new GeneralSecurityException("Encryption failed", e);
        }
    }
}