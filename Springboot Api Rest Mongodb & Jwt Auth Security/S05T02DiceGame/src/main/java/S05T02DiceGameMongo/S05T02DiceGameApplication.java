package S05T02DiceGameMongo;

import S05T02DiceGameMongo.Model.Repository.DiceGameRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@EnableMongoRepositories(basePackageClasses = DiceGameRepository.class)
public class S05T02DiceGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(S05T02DiceGameApplication.class, args);
	}

}
