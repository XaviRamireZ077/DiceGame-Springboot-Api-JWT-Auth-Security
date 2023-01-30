package S05T02N01.DiceGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class DiceGameApplication {

	public static void main(String[] args) {

		SpringApplication.run(DiceGameApplication.class, args);
	}
}
