package bg.tu_sofia.blockchain_svetoslav_evtim.controller;

import bg.tu_sofia.blockchain_svetoslav_evtim.request.TransactionRequest;
import bg.tu_sofia.blockchain_svetoslav_evtim.service.PrivateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/private/blockchain")
@Tag(name = "Private Blockchain", description = "Endpoints for private blockchain operations")
public class PrivateController {

    private final PrivateService privateService;

    
    public PrivateController(PrivateService privateService) {
        this.privateService = privateService;
    }

    @GetMapping("/keys")
    @Operation(summary = "Generate keys", description = "Generates a new pair of public and private keys")
    public Map<String, String> generateKeys() throws Exception {
        return privateService.generateKeys();
    }

    @PostMapping("/create-transaction")
    @Operation(summary = "Create and sign transaction", description = "Creates and signs a new transaction")
    public Map<String, Object> createAndSignTransaction(@RequestBody TransactionRequest request) throws Exception {
        return privateService.createAndSignTransaction(request);
    }
}
