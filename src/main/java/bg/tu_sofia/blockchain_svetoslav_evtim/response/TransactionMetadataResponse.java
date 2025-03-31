package bg.tu_sofia.blockchain_svetoslav_evtim.response;

public class TransactionMetadataResponse {
    private String metadata;

    public TransactionMetadataResponse(String metadata) {
        this.metadata = metadata;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}