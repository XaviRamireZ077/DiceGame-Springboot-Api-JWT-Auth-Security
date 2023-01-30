package S05T02N01.DiceGame.Model.Repository;

import S05T02N01.DiceGame.Model.Domain.Player;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface PlayerRepository extends JpaRepository<Player,Integer> {

    Boolean existsByPlayerName (String playerName);

    Player findById(Long player_id);


    //Player findByPlayerName (String playerName);


    List<Player> findAll();









}
