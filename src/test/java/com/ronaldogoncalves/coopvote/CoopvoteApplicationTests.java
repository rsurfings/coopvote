package com.ronaldogoncalves.coopvote;

import com.ronaldogoncalves.coopvote.configuration.KafkaConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(classes = KafkaConfiguration.class, loader = AnnotationConfigContextLoader.class)
@SpringBootTest
class CoopvoteApplicationTests {

	@Test
	void contextLoads() {
	}

}
