package S05T02DiceGameMongo.Model.Repository;

import S05T02DiceGameMongo.Model.Domain.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PlayerRepository extends MongoRepository<Player,Integer> {

    boolean existsByUserName (String userName);

    Player findById (String playerId);

    List<Player> findAll();

    @Query(value = "{userName:'?0'}")
    Player findUserByUserName(String userName);












}
