package S05T02DiceGameMongo.Model.Repository;

import S05T02DiceGameMongo.Model.Domain.DiceGame;
import S05T02DiceGameMongo.Model.Domain.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface DiceGameRepository extends MongoRepository<DiceGame,Long> {

    List<DiceGame> getRollDiceGameByPlayerId(String playerId);

    void deleteByPlayer(Player player);
}

