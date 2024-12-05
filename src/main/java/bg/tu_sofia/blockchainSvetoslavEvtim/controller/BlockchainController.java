package bg.tu_sofia.blockchainSvetoslavEvtim.controller;

import bg.tu_sofia.blockchainSvetoslavEvtim.model.Block;
import bg.tu_sofia.blockchainSvetoslavEvtim.model.Blockchain;
import bg.tu_sofia.blockchainSvetoslavEvtim.model.Transaction;
import bg.tu_sofia.blockchainSvetoslavEvtim.request.RealTransactionRequest;
import bg.tu_sofia.blockchainSvetoslavEvtim.response.TransactionMetadataResponse;
import bg.tu_sofia.blockchainSvetoslavEvtim.service.BlockchainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blockchain")
@Tag(name = "Blockchain", description = "Endpoints for blockchain operations")
public class BlockchainController {

    private final BlockchainService blockchainService;

    public BlockchainController(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    @GetMapping
    @Operation(summary = "Get blockchain", description = "Retrieves the entire blockchain")
    public Blockchain getBlockchain() {
        return blockchainService.getBlockchain();
    }

    @PostMapping("/transaction")
    @Operation(summary = "Create transaction", description = "Creates a new encrypted transaction")
    public Transaction createTransaction(@RequestBody RealTransactionRequest request) throws Exception {
        return blockchainService.createEncryptedTransaction(request);
    }

    @GetMapping("/balance/{address}")
    @Operation(summary = "Get balance", description = "Retrieves the balance for a given address")
    public double getBalance(@PathVariable String address) {
        return blockchainService.getBalance(address);
    }

    @PostMapping("/mine/{minerAddress}")
    @Operation(summary = "Mine block", description = "Mines a new block and adds it to the blockchain")
    public Blockchain mineBlock(@PathVariable String minerAddress) {
        return blockchainService.mineBlock(minerAddress);
    }

    @GetMapping("/latest")
    @Operation(summary = "Get latest block", description = "Retrieves the latest block in the blockchain")
    public Block getLatestBlock() {
        return blockchainService.getBlockchain().getLatestBlock();
    }

    @GetMapping("/validate")
    @Operation(summary = "Validate blockchain", description = "Validates the entire blockchain")
    public boolean validateBlockchain() {
        return blockchainService.validateBlockchain();
    }

    @GetMapping("/transaction/{transactionId}/metadata")
    @Operation(summary = "Get transaction metadata", description = "Retrieves metadata for a given transaction")
    public TransactionMetadataResponse getTransactionMetadata(@PathVariable String transactionId) {
        return blockchainService.getTransactionMetadata(transactionId);
    }
}