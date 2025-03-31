package bg.tu_sofia.blockchainSvetoslavEvtim;

import bg.tu_sofia.blockchainSvetoslavEvtim.config.BlockchainConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(BlockchainConfig.class)
public class BlockchainSvetoslavEvtimApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockchainSvetoslavEvtimApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			System.out.println("Blockchain started...");
		};
	}
}
