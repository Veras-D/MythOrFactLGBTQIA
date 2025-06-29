package com.veras.mythOrFactLGBT;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "dotenv.path=./.env")
class MythOrFactLgbtApplicationTests {

	@Test
	void contextLoads() {
	}

}
