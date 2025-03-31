package bg.tu_sofia.blockchain_svetoslav_evtim.service;

import bg.tu_sofia.blockchain_svetoslav_evtim.config.BlockchainConfig;
import bg.tu_sofia.blockchain_svetoslav_evtim.model.Block;
import bg.tu_sofia.blockchain_svetoslav_evtim.model.Blockchain;
import bg.tu_sofia.blockchain_svetoslav_evtim.model.Transaction;
import bg.tu_sofia.blockchain_svetoslav_evtim.request.RealTransactionRequest;
import bg.tu_sofia.blockchain_svetoslav_evtim.response.TransactionMetadataResponse;
import bg.tu_sofia.blockchain_svetoslav_evtim.utils.CbcUtils;
import bg.tu_sofia.blockchain_svetoslav_evtim.utils.HashUtils;
import bg.tu_sofia.blockchain_svetoslav_evtim.utils.KeyUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class BlockchainService {

    private final Blockchain blockchain;
    private List<Transaction> pendingTransactions;
    private final BlockchainConfig blockchainConfig;

    @Value("${encryption.key}")
    private String encryptionKey;

    public BlockchainService(BlockchainConfig blockchainConfig) {
        this.blockchain = new Blockchain();
        this.pendingTransactions = new ArrayList<>();
        this.blockchainConfig = blockchainConfig;
    }

    public Transaction createEncryptedTransaction(RealTransactionRequest request) throws Exception {
        PublicKey senderPublicKey = KeyUtils.getPublicKeyFromBase64(request.getPublicKey());
        String senderAddress = KeyUtils.getAddressFromPublicKey(senderPublicKey);
        Transaction transaction = new Transaction(senderAddress, request.getReceiver(), request.getAmount(), request.getMetadata());

        byte[] signatureBytes = Base64.getDecoder().decode(request.getSignature());
        transaction.setSignature(signatureBytes);

        if (!transaction.verifyTransaction(senderPublicKey)) {
            throw new IllegalArgumentException("Invalid transaction signature");
        }

        double senderBalance = getBalance(transaction.getSender());

        if (senderBalance < transaction.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        if (!addressExists(transaction.getReceiver())) {
            throw new IllegalArgumentException("Receiver address does not exist");
        }

        CbcUtils.EncryptionResult encrypted = CbcUtils.encrypt(transaction.getMetadata(), encryptionKey);

        transaction.setIv(encrypted.getIv());
        transaction.setEncryptedData(encrypted.getCiphertext());

        pendingTransactions.add(transaction);

        return transaction;
    }

    public boolean addressExists(String address) {
        for (Block block : blockchain.getChain()) {
            for (Transaction transaction : block.getTransactions()) {
                if (transaction.getSender().equals(address) || transaction.getReceiver().equals(address)) {
                    return true;
                }
            }
        }
        return false;
    }

    public double getBalance(String address) {
        double balance = 0;

        for (Block block : blockchain.getChain()) {
            for (Transaction transaction : block.getTransactions()) {
                if (transaction.getReceiver().equals(address)) {
                    balance += transaction.getAmount();
                }
                if (transaction.getSender().equals(address)) {
                    balance -= transaction.getAmount();
                }
            }
        }

        return balance;
    }

    // Mine a block with the pending transactions
    public Blockchain mineBlock(String minerAddress) {
        Block previousBlock = blockchain.getLatestBlock();

        // Create a new block
        Block newBlock = new Block(previousBlock.getBlockHash());

        // Add mining reward transaction
        Transaction rewardTransaction = new Transaction("Block Reward", minerAddress, blockchainConfig.getMiningReward(), "Mining Reward");
        newBlock.addTransaction(rewardTransaction);

        // Add transactions from the mempool to the block, up to the maximum limit
        int transactionCount = 0;
        for (Transaction transaction : pendingTransactions) {
            if (transactionCount >= blockchainConfig.getMaxTransactionsPerBlock()) {
                break;
            }
            newBlock.addTransaction(transaction);
            transactionCount++;
        }

        // Start mining (finding valid hash)
        String blockData;
        String blockHash;
        int nonce = 0;
        do {
            blockData = String.format("%s|%d|%s|%d",
                    newBlock.getPreviousHash(),
                    newBlock.getTimestamp(),
                    newBlock.getTransactions().toString(),
                    nonce);
            blockHash = HashUtils.calculateSHA256(blockData);
            nonce++;
        } while (!blockHash.startsWith("0".repeat(blockchainConfig.getDifficulty())));

        // Set block hash and nonce
        newBlock.setBlockHash(blockHash);
        newBlock.setNonce(nonce - 1);

        this.blockchain.addBlock(newBlock);

        pendingTransactions = pendingTransactions.subList(transactionCount, pendingTransactions.size());

        return blockchain;
    }

    // Get the blockchain
    public Blockchain getBlockchain() {
        return blockchain;
    }

    // Validate the blockchain integrity
    public boolean validateBlockchain() {
        List<Block> chain = blockchain.getChain();

        // Loop through the chain to validate hashes and previous hash links
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            // Validate current block's hash
            String calculatedHash = HashUtils.calculateSHA256(
                    String.format("%s|%d|%s|%d",
                            current.getPreviousHash(),
                            current.getTimestamp(),
                            current.getTransactions().toString(),
                            current.getNonce())
            );
            if (!calculatedHash.equals(current.getBlockHash())) {
                return false; // Invalid hash
            }

            // Validate linkage to the previous block
            if (!current.getPreviousHash().equals(previous.getBlockHash())) {
                return false; // Broken chain
            }
        }

        return true; // Blockchain is valid
    }

    public TransactionMetadataResponse getTransactionMetadata(String transactionId) {
        for (Block block : blockchain.getChain()) {
            for (Transaction transaction : block.getTransactions()) {
                if (transaction.getId().equals(transactionId)) {
                    String decryptedMetadata = CbcUtils.decrypt(transaction.getEncryptedData(), transaction.getIv(), encryptionKey);
                    return new TransactionMetadataResponse(decryptedMetadata);
                }
            }
        }
        throw new IllegalArgumentException("Transaction not found");
    }
}