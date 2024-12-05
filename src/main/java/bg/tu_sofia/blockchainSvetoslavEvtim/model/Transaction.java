package bg.tu_sofia.blockchainSvetoslavEvtim.model;

import bg.tu_sofia.blockchainSvetoslavEvtim.utils.KeyUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;

public class Transaction {
    private String id;
    private String sender;
    private String receiver;
    private Double amount;
    private String metadata;
    private String encryptedData;
    private String iv;
    private byte[] signature;

    public Transaction(String sender, String receiver, Double amount, String metadata) {
        this.id = UUID.randomUUID().toString();
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.metadata = metadata;
    }

    public void signTransaction(PrivateKey privateKey) throws Exception {
        String data = sender + receiver + amount + metadata;
        this.signature = KeyUtils.sign(data, privateKey);
    }

    public boolean verifyTransaction(PublicKey publicKey) throws Exception {
        String data = sender + receiver + amount + metadata;
        return KeyUtils.verify(data, signature, publicKey);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
}
