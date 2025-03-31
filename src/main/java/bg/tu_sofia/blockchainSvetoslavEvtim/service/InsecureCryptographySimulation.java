package bg.tu_sofia.blockchainSvetoslavEvtim.service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class InsecureCryptographySimulation {

    public String encrypt(String data) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES"); // DES is considered insecure
        SecretKey secretKey = keyGen.generateKey();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }
}