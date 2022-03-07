package jinny.toy.luckynumber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class LuckyNumberApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuckyNumberApplication.class, args);
	}

}
