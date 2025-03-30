package bg.tu_sofia.blockchainSvetoslavEvtim;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BlockchainSvetoslavEvtimApplicationTests {

	@Test
	void contextLoads() {
		// test test test
	}

	@Test
	void testFailed() {
		assertThat(true).isTrue();
		// test test test test test
	}

}
