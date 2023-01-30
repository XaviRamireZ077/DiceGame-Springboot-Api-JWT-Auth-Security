package S05T02N01.DiceGame.Controller;

import S05T02N01.DiceGame.Model.Exceptions.PlayerNotFoundException;
import S05T02N01.DiceGame.Model.Domain.DiceGame;
import S05T02N01.DiceGame.Model.Domain.Player;
import S05T02N01.DiceGame.Model.Service.Implementation.DiceGameServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Xavier Ramirez
 * URL’s:
 * POST: /players: crea un jugador/a.
 * PUT /players: modifica el nom del jugador/a.
 * POST /players/{id}/games/ : un jugador/a específic realitza una tirada dels daus.
 * DELETE /players/{id}/games: elimina les tirades del jugador/a.
 * GET /players/: retorna el llistat de tots els jugadors/es del sistema amb el seu percentatge mitjà d’èxits.
 * GET /players/{id}/games: retorna el llistat de jugades per un jugador/a.
 * GET /players/ranking: retorna el ranking mig de tots els jugadors/es del sistema. És a dir, el percentatge mitjà d’èxits.
 * GET /players/ranking/loser: retorna el jugador/a amb pitjor percentatge d’èxit.
 * GET /players/ranking/winner: retorna el jugador amb pitjor percentatge d’èxit.
 *
**/

@RestController
@RequiredArgsConstructor

@RequestMapping("/player")
@CrossOrigin(origins = "*", methods= { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
public class GameController {


    @Autowired
    DiceGameServiceImpl dgService;
    @PostMapping("/add")
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        return ResponseEntity.ok().body(dgService.addPlayer(player));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updatePlayer(
            @RequestBody Player player,
            @PathVariable("id") Long id
    ){

        return ResponseEntity.ok().body(dgService.updatePlayer(id, player));
    }

    @PostMapping("/{id}/rollDices")
    public ResponseEntity<DiceGame> RollTheDices(@PathVariable(value="id") Long id) throws Exception{
        return ResponseEntity.ok().body(dgService.rollTheDices(id));
    }

    @DeleteMapping("/{id}/deleteRollDices")
    public ResponseEntity deleteRollsByPlayer(@PathVariable(value = "id") Long id) throws Exception{
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
    public List<DiceGame> getGamesByPlayer(@PathVariable(value = "id") Long id) throws Exception{
        try {
            if(id != null && dgService.getPlayerGames(id) != null){
                return dgService.getPlayerGames(id);
            } else{
                throw new PlayerNotFoundException("Player with id -> " + id + " does not exist!!!");
            }
        } catch (Exception e){
            System.out.println("Error -> " + e.getMessage());
        }
        return null;
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

    @GetMapping("/{id}")
    @ResponseBody
    public Player getPlayerById(@PathVariable(value = "id") Long id) throws Exception{
        return dgService.findPlayer(id);
    }

    @GetMapping("/apiPlayers")
    @ResponseBody
    public List<Player> getListOfAllPlayers() throws Exception{
        return dgService.listOfPlayers();
    }




}
