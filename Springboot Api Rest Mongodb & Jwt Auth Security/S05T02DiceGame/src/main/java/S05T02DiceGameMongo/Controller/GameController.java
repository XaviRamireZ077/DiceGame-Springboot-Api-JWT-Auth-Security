package S05T02DiceGameMongo.Controller;

import S05T02DiceGameMongo.Model.Domain.DiceGame;
import S05T02DiceGameMongo.Model.Domain.Player;
import S05T02DiceGameMongo.Model.Exceptions.PlayerNotFoundException;
import S05T02DiceGameMongo.Model.Service.Implementation.DiceGameServiceImpl;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/player")
@CrossOrigin(origins = "*", methods= { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
public class GameController {

    @Autowired(required = true)
    DiceGameServiceImpl dgService;

    @PostMapping("/add")
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        return ResponseEntity.ok().body(dgService.addPlayer(player));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updatePlayer(
            @RequestBody Player player,
            @PathVariable("id") String id
    ){

        return ResponseEntity.ok().body(dgService.updatePlayer(id, player));
    }

    @PostMapping("/{id}/rollDices")
    public ResponseEntity<DiceGame> addRollDices(@PathVariable(value="id") String id) throws Exception{
        return ResponseEntity.ok().body(dgService.rollTheDices(id));
    }

    @DeleteMapping("/{id}/deleteRollDices")
    public ResponseEntity deleteGameByPlayer(@PathVariable(value = "id") String id) throws Exception{
        try {
            if(id != null && dgService.findPlayer(id) != null){
                dgService.deleteGameByPlayerId(id);
                return ResponseEntity.ok().build();
            } else{
                throw new PlayerNotFoundException("Player with id -> " + id + " does not exist!!!");
            }

        } catch (Exception e){
            System.out.println("Error ==>> " + e.getMessage());
        }
        return null;
    }

    @GetMapping("/playersRating")
    @ResponseBody
    public Map<String, Double> getAllPlayersSuccessRate() throws Exception{
        return dgService.getPlayersRating();
    }

    @GetMapping("/{id}/games")
    @ResponseBody
    public List<DiceGame> getGamesByPlayer(@PathVariable(value = "id") String id) throws Exception{
        try {
            if(id != null && dgService.getPlayerGames(id) != null){
                return dgService.getPlayerGames(id);
            } else{
                throw new PlayerNotFoundException("Player with id -> " + id + " does not exist!!!");
            }
        } catch (Exception e){
            System.out.println("Error -> " + e.getMessage());
        }
        return Collections.emptyList();
    }


    @GetMapping("/ranking")
    @ResponseBody
    public List<Player> getPlayersRanking() throws Exception{
        return dgService.getPlayersRanking();
    }

    @GetMapping("/ranking/loser")
    @ResponseBody
    public Player getLoserPlayer() throws Exception{
        return dgService.getLoserPlayer();
    }

    @GetMapping("/ranking/winner")
    @ResponseBody
    public Player getWinPlayer() throws Exception{
        return dgService.getWinPlayer();
    }

    @GetMapping("/apiPlayers")
    @ResponseBody
    public List<Player> getListOfAllPlayers() throws Exception{
        return dgService.listOfPlayers();
    }


    @GetMapping("/{id}")
    @ResponseBody
    public Player getPlayerById(@PathVariable(value = "id") String id) throws Exception{
        return dgService.findPlayer(id);
    }

}
