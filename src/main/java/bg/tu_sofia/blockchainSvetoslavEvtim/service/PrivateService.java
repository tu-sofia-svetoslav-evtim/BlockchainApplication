package bg.tu_sofia.blockchainSvetoslavEvtim.service;

import bg.tu_sofia.blockchainSvetoslavEvtim.model.Transaction;
import bg.tu_sofia.blockchainSvetoslavEvtim.request.TransactionRequest;
import bg.tu_sofia.blockchainSvetoslavEvtim.utils.KeyUtils;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class PrivateService {

    public Map<String, String> generateKeys() throws Exception {
        KeyPair keyPair = KeyUtils.generateKeyPair();
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        Map<String, String> keys = new HashMap<>();
        keys.put("publicKey", publicKey);
        keys.put("privateKey", privateKey);

        return keys;
    }

    public Map<String, Object> createAndSignTransaction(TransactionRequest request) throws Exception {
        PrivateKey privateKey = KeyUtils.getPrivateKeyFromBase64(request.getPrivateKey());
        PublicKey publicKey = KeyUtils.getPublicKeyFromBase64(request.getPublicKey());
        String senderAddress = KeyUtils.getAddressFromPublicKey(publicKey);

        Transaction transaction = new Transaction(senderAddress, request.getReceiver(), request.getAmount(), request.getMetadata());
        transaction.signTransaction(privateKey);

        Map<String, Object> response = new HashMap<>();
        response.put("sender", transaction.getSender());
        response.put("receiver", transaction.getReceiver());
        response.put("amount", transaction.getAmount());
        response.put("metadata", transaction.getMetadata());
        response.put("signature", Base64.getEncoder().encodeToString(transaction.getSignature()));

        return response;
    }
}