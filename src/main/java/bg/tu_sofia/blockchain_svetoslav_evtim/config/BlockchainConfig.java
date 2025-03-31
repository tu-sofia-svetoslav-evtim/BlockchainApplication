package bg.tu_sofia.blockchainSvetoslavEvtim.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "blockchain")
public class BlockchainConfig {
    private int difficulty;
    private double miningReward;
    private int maxTransactionsPerBlock;

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public double getMiningReward() {
        return miningReward;
    }

    public void setMiningReward(double miningReward) {
        this.miningReward = miningReward;
    }

    public int getMaxTransactionsPerBlock() {
        return maxTransactionsPerBlock;
    }

    public void setMaxTransactionsPerBlock(int maxTransactionsPerBlock) {
        this.maxTransactionsPerBlock = maxTransactionsPerBlock;
    }
}