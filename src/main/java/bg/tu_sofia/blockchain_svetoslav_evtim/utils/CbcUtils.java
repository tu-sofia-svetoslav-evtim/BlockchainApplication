package bg.tu_sofia.blockchain_svetoslav_evtim.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class CbcUtils {

    public static class EncryptionResult {
        private final String ciphertext;
        private final String iv;

        public EncryptionResult(String ciphertext, String iv) {
            this.ciphertext = ciphertext;
            this.iv = iv;
        }

        public String getCiphertext() {
            return ciphertext;
        }

        public String getIv() {
            return iv;
        }
    }


    public static EncryptionResult encrypt(String plaintext, String key) {
        try {
            // Generate a random IV
            byte[] ivBytes = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(ivBytes);
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

            // Create the AES key
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

            // Initialize the cipher in CBC mode
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            // Encrypt the plaintext
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());

            // Encode the IV and ciphertext to Base64 for easier transport
            String ciphertext = Base64.getEncoder().encodeToString(encryptedBytes);
            String iv = Base64.getEncoder().encodeToString(ivBytes);

            return new EncryptionResult(ciphertext, iv);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    // Decrypt ciphertext using AES in CBC mode
    public static String decrypt(String ciphertext, String iv, String key) {
        try {
            // Decode Base64 IV and ciphertext
            byte[] ivBytes = Base64.getDecoder().decode(iv);
            byte[] encryptedBytes = Base64.getDecoder().decode(ciphertext);

            // Create the AES key
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

            // Initialize the cipher in CBC mode
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(ivBytes));

            // Decrypt the ciphertext
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}
