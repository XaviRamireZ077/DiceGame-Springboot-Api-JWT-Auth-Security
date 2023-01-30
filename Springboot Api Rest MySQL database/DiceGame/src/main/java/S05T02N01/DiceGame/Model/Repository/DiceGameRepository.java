package S05T02N01.DiceGame.Model.Repository;

import S05T02N01.DiceGame.Model.Domain.DiceGame;
import S05T02N01.DiceGame.Model.Domain.Player;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Transactional
public interface DiceGameRepository extends JpaRepository<DiceGame,Long> {

    void deleteByPlayer(Player player);

    List<DiceGame> getRollDiceGameByPlayerId(Long player_id);

}
