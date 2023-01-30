package S05T02DiceGameMongo.Model.Service;

import S05T02DiceGameMongo.Model.Domain.DiceGame;
import S05T02DiceGameMongo.Model.Domain.Player;

import java.util.List;
import java.util.Map;
public interface DiceGameService {

    // CRUD METHODS

    // add player & Save
    public Player addPlayer(Player player);

    // Update player
    public Player updatePlayer(String id ,Player player);

    // Get all players
    public List<Player> listOfPlayers();

    // Get players success-rating
    public Map<String, Double> getPlayersRating();

    // Get player ranking
    public List<Player> getPlayersRanking();

    // Find players by id
    public Player findPlayer(String playerId);

    // Roll the dices
    public DiceGame rollTheDices(String playerId);

    // Get players games
    public List<DiceGame> getPlayerGames(String playerId);

    // Delete Game from a player by id
    public void deleteGameByPlayerId(String playerId);

    public Player getLoserPlayer();

    public Player getWinPlayer();

}
