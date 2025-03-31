package bg.tu_sofia.blockchain_svetoslav_evtim.model;

import bg.tu_sofia.blockchain_svetoslav_evtim.utils.KeyUtils;

import java.security.KeyPair;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

public class Blockchain {
    private final LinkedList<Block> chain;

    public Blockchain() {
        chain = new LinkedList<>();
        Block genesisBlock = new Block("0");
        genesisBlock.setBlockHash("genesis_hash");
        chain.add(genesisBlock);
        initializeGenesisTransaction();
    }


    private void initializeGenesisTransaction() {
        try {
            KeyPair keyPair = KeyUtils.generateKeyPair();
            System.out.println("Private key of first address: " + Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
            String senderAddress = KeyUtils.getAddressFromPublicKey(keyPair.getPublic());
            Transaction genesisTransaction = new Transaction("System", senderAddress, 50.26921689, "Genesis Transaction");
            addGenesisTransaction(genesisTransaction);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing genesis transaction", e);
        }
    }

    private void addGenesisTransaction(Transaction transaction) {
        Block genesisBlock = chain.getFirst();
        genesisBlock.addTransaction(transaction);
    }
    public void addBlock(Block block) {
        this.chain.add(block);
    }

    public Block getLatestBlock() {
        return chain.getLast();
    }

    public List<Block> getChain() {
        return chain;
    }
}
